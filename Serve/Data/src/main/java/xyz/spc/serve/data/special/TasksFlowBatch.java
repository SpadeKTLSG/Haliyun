package xyz.spc.serve.data.special;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.data.flow.TasksFlow;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksFlowBatch {


    // Feign
    private final ClustersClient clustersClient;

    // Func
    private final FilesFunc filesFunc;
    private final DownloadTaskFunc downloadTaskFunc;
    private final UploadTaskFunc uploadTaskFunc;

    //Flow
    private final TasksFlow tasksFlow;


    // 线程池
    @Qualifier("SKThreadPoolTExecutor")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;


    /**
     * 上传文件批量处理接口
     */
    public void uploadFileBatch(MultipartFile[] files, Long clusterId, Long userId) throws InterruptedException {

        // 1 日志模块业务: 记录批量处理的总耗时等信息, 在开头进行计时等处理

        // 1.1 开始计时:
        long startTime = System.currentTimeMillis();


        // 2 使用 异步线程池 + 汇总的模式进行批处理.
        List<CompletableFuture<Map<String, Object>>> futures = new ArrayList<>();

        // 2.1 初始化 CountDownLatch
        int total = files.length;
        CountDownLatch latch = new CountDownLatch(total);

        // 3 针对每次处理, 返回对应的耗时, 最后汇总 (整合包装方法, 在下面)


        for (MultipartFile file : files) {

            CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    // 3.1 调用单个文件上传方法并返回结果
                    return this.uploadFileWithAroundLog(file, clusterId, userId);

                } catch (IOException e) {
                    return Map.of("excuteTime", -1L); // -1 代表失败
                } finally {
                    // 3.2 任务完成后减少计数
                    latch.countDown();
                }

            }, threadPoolTaskExecutor); //调用线程池执行异步任务

            futures.add(future); // 汇总
        }

        // 3.3 等待所有任务完成, 限时最大等待时间 = 6000000秒, AKA 100分钟
        boolean await = latch.await(6000000, TimeUnit.SECONDS);
        if (!await) {
            throw new ServiceException("批量上传任务超时");
        }

        // 4 所有任务完成后汇总结果

        List<Map<String, Object>> results = new ArrayList<>();

        for (CompletableFuture<Map<String, Object>> future : futures) {
            try {
                results.add(future.get()); // 获取每个任务的结果
            } catch (InterruptedException | ExecutionException e) {
                log.error("批量上传任务获取结果失败", e);
            }
        }

        // 1.X 结束计时:
        long endTime = System.currentTimeMillis();

        // 1.X+1 计算耗时并调用日志模块内容
        // (TODO调用, 这里先记录)
        log.info("批量上传文件耗时: {}ms", endTime - startTime); // 未来加上用户id等信息
        log.info("批量上传任务详情: {}", results); // 未来加上用户id等信息

    }


    /**
     * 上传文件流处理包装, 记录操作耗时等日志
     *
     * @param file      上传的文件对象
     * @param clusterId 群组id
     */
    public Map<String, Object> uploadFileWithAroundLog(MultipartFile file, Long clusterId, Long userId) throws IOException {

        // 1 日志模块业务: 记录批量处理的总耗时等信息, 在开头进行计时等处理

        // 1.1 开始计时:
        long startTime = System.currentTimeMillis();

        // 2 调用单个上传接口
        tasksFlow.uploadFile(file, clusterId, userId);

        // 1.2 结束计时:
        long endTime = System.currentTimeMillis();

        // 1.3 返回单条记录

        return Map.of(
                "excuteTime", endTime - startTime
        );
    }


    /**
     * 下载文件批量处理接口
     */
    public void downloadFileBatch(List<Long> fileIds, Long creatorUserId, Long fromClusterId, HttpServletResponse response) throws InterruptedException {


        // 1 日志模块业务: 记录批量处理的总耗时等信息, 在开头进行计时等处理

        // 1.1 开始计时:
        long startTime = System.currentTimeMillis();


        // 2 使用 异步线程池 + 汇总的模式进行批处理.
        List<CompletableFuture<Map<String, Object>>> futures = new ArrayList<>();

        // 2.1 初始化 CountDownLatch
        int total = fileIds.size();
        CountDownLatch latch = new CountDownLatch(total);

        // 3 针对每次处理, 返回对应的耗时, 最后汇总 (整合包装方法, 在下面)

        for (Long fileId : fileIds) {

            CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    // 3.2 调用单个文件下载方法并返回结果
                    return this.downloadFileWithAroundLog(fileId, creatorUserId, fromClusterId);

                } catch (IOException e) {
                    return Map.of("excuteTime", -1L); // -1 代表失败

                } finally {
                    // 3.3 任务完成后减少计数
                    latch.countDown();
                }

                // 3.4 调用线程池执行异步任务
            }, threadPoolTaskExecutor);

            futures.add(future); // 汇总
        }


        // 3.5 等待所有任务完成, 限时最大等待时间 = 6000000秒, AKA 100分钟
        boolean await = latch.await(6000000, TimeUnit.SECONDS);
        if (!await) {
            throw new ServiceException("批量下载任务超时");
        }


        // 4 所有任务完成后汇总结果

        // 4.1 汇总结果: 需要把两段的Map分开为 time Map 和 file list
        List<Map<String, Object>> timeRes = new ArrayList<>();
        List<File> fileResList = new ArrayList<>();

        for (CompletableFuture<Map<String, Object>> future : futures) {
            try {

                // 4.2 进行拆分
                Map<String, Object> resTemp = future.get(); // 获取每个任务的总结果

                // time res
                Map<String, Object> timeResMap = Map.of("excuteTime", resTemp.get("excuteTime")); // 获取时间
                timeRes.add(timeResMap);

                // file res
                File fileRes = (File) resTemp.get("realFile"); // 获取文件
                fileResList.add(fileRes);


            } catch (InterruptedException | ExecutionException e) {
                log.error("批量上传任务获取结果失败", e);
            }
        }


        // 5 进行文件压缩处理并返回用户
        downloadTaskFunc.download2ClientBatch(fileResList, response);

        // 1.X 结束计时:
        long endTime = System.currentTimeMillis();

        // 1.X+1 计算耗时并调用日志模块内容
        // (TODO调用, 这里先记录)
        log.info("批量上传文件耗时: {}ms", endTime - startTime); // 未来加上用户id等信息
        log.info("批量上传任务详情: {}", timeRes); // 未来加上用户id等信息

    }


    /**
     * 下载文件流处理包装, 记录操作耗时等日志
     */
    public Map<String, Object> downloadFileWithAroundLog(Long fileId, Long userId, Long clusterId) throws IOException {

        // 1 日志模块业务: 记录批量处理的总耗时等信息, 在开头进行计时等处理

        // 1.1 开始计时:
        long startTime = System.currentTimeMillis();

        // 2 调用单个下载接口
        File realFile = tasksFlow.downloadFileNotResp(fileId, userId, clusterId);

        // 1.2 结束计时:
        long endTime = System.currentTimeMillis();


        // 1.3 返回单条记录, 包括了日志记录和文件对象

        return Map.of(
                "excuteTime", endTime - startTime,
                "realFile", realFile
        );
    }


}
