package xyz.spc.serve.data.special;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
import java.util.concurrent.ExecutionException;

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
    @Async("SKThreadPoolTExecutor")
    public void uploadFileBatch(MultipartFile[] files, Long clusterId, Long userId) {

        // 1 日志模块业务: 记录批量处理的总耗时等信息, 在开头进行计时等处理

        // 1.1 开始计时:
        long startTime = System.currentTimeMillis();


        // 2 使用 异步线程池 + 汇总的模式进行批处理.
        List<CompletableFuture<Map<String, Object>>> futures = new ArrayList<>();

        // 3 针对每次处理, 返回对应的耗时, 最后汇总 (整合包装方法, 在下面)

        for (MultipartFile file : files) {

            CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    // 调用单个文件上传方法并返回结果
                    return this.uploadFileWithAroundLog(file, clusterId, userId);

                } catch (IOException e) {
                    return Map.of("excuteTime", -1L); // -1 代表失败
                }

            }, threadPoolTaskExecutor); //调用线程池执行异步任务

            futures.add(future); // 汇总
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
    @Async("SKThreadPoolTExecutor")
    public void downloadFileBatch(List<Long> fileIds, Long creatorUserId, Long fromClusterId, HttpServletResponse response) {


        // 1 日志模块业务: 记录批量处理的总耗时等信息, 在开头进行计时等处理

        // 1.1 开始计时:
        long startTime = System.currentTimeMillis();


        // 2 使用 异步线程池 + 汇总的模式进行批处理.
        List<CompletableFuture<Map<String, Object>>> futures = new ArrayList<>();

        // 3 针对每次处理, 返回对应的耗时, 最后汇总 (整合包装方法, 在下面)

        for (Long fileId : fileIds) {

            CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    // 调用单个文件下载方法并返回结果
                    return this.downloadFileWithAroundLog(fileId, creatorUserId, fromClusterId);

                } catch (IOException e) {
                    return Map.of("excuteTime", -1L); // -1 代表失败
                }

            }, threadPoolTaskExecutor); //调用线程池执行异步任务

            futures.add(future); // 汇总
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
     * 下载文件流处理包装, 记录操作耗时等日志
     */
    public Map<String, Object> downloadFileWithAroundLog(Long fileId, Long clusterId, Long userId) throws IOException {

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
