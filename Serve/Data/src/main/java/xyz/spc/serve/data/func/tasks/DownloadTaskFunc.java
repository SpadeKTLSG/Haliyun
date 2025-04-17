package xyz.spc.serve.data.func.tasks;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.common.constant.UploadDownloadCT;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Data.tasks.DownloadTaskDO;
import xyz.spc.domain.model.Data.tasks.DownloadTask;
import xyz.spc.gate.vo.Data.tasks.DownloadTaskVO;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.infra.special.Data.tasks.TasksRepo;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static cn.hutool.core.io.FileUtil.getOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadTaskFunc {

    /**
     * Repo
     */
    private final TasksRepo tasksRepo;
    private final HdfsRepo hdfsRepo;


    /**
     * 任务表的任务创建 (下载任务比较简单)
     */
    public void taskGen(Long fileId, String fileName, Long creatortUserId) {

        Long id = SnowflakeIdUtil.nextId();

        DownloadTaskDO downTaskDO = DownloadTaskDO.builder().id(id).fileId(fileId).fileName(fileName).pid(fileId).userId(creatortUserId).status(DownloadTask.STATUS_NOT_START) //未开始
                .fileSizeTotal(0L)  // 文件总大小 (模拟)
                .fileSizeOk(0L) // 已完成的文件大小 (模拟)
                .executor(DownloadTask.EXECUTOR_LOCAL) // 执行器 (本地)
                .build();


        tasksRepo.downloadTaskService.save(downTaskDO);

    }


    /**
     * 通过 id 查具体的任务信息
     */
    public DownloadTaskVO getTaskInfo(Long taskId) {

        // 获取
        DownloadTaskDO downloadTaskDO = tasksRepo.downloadTaskService.getById(taskId);

        if (downloadTaskDO == null) {
            throw new ServiceException("任务不存在");
        }

        return DownloadTaskVO.builder().id(downloadTaskDO.getId()).fileId(downloadTaskDO.getFileId()).fileName(downloadTaskDO.getFileName()).pid(downloadTaskDO.getPid()).userId(downloadTaskDO.getUserId()).status(downloadTaskDO.getStatus()).fileSizeTotal(downloadTaskDO.getFileSizeTotal()).fileSizeOk(downloadTaskDO.getFileSizeOk()).executor(downloadTaskDO.getExecutor()).build();
    }

    /**
     * 下载任务开始
     */
    public void startTask(Long taskId) {

        tasksRepo.downloadTaskService.update(Wrappers.lambdaUpdate(DownloadTaskDO.class).set(DownloadTaskDO::getStatus, DownloadTask.STATUS_RUNNING) // 运行中
                .eq(DownloadTaskDO::getId, taskId) // 任务id
                .eq(DownloadTaskDO::getExecutor, DownloadTask.EXECUTOR_LOCAL) // note: 绑定对应执行机器, 实现唯一对应. 本地执行器
        );

    }

    /**
     * 下载任务完成
     */
    public void completeTask(Long taskId) {

        tasksRepo.downloadTaskService.update(Wrappers.lambdaUpdate(DownloadTaskDO.class).set(DownloadTaskDO::getStatus, DownloadTask.STATUS_FINISH) // 完成
                .eq(DownloadTaskDO::getId, taskId) // 任务id
                .eq(DownloadTaskDO::getExecutor, DownloadTask.EXECUTOR_LOCAL) // note: 绑定对应执行机器, 实现唯一对应. 本地执行器
        );
    }

    /**
     * 登记失败任务
     */
    @Async
    public void failTask(Long taskId) {

        tasksRepo.downloadTaskService.update(Wrappers.lambdaUpdate(DownloadTaskDO.class).set(DownloadTaskDO::getStatus, DownloadTask.STATUS_FAIL) // 失败
                .eq(DownloadTaskDO::getId, taskId) // 任务id
                .eq(DownloadTaskDO::getExecutor, DownloadTask.EXECUTOR_LOCAL) // note: 绑定对应执行机器, 实现唯一对应. 本地执行器
        );
    }


    /**
     * HDFS 下载到本地 Temp 文件
     */
    public String handleTempDownload(String fileName, Long creatorUserId, Long fromClusterId) {
        String filePathInHDFS = "/";
        OutputStream os = null;

        try {
            //  定位 HDFS 文件路径

            filePathInHDFS = filePathInHDFS + creatorUserId + "/" + fromClusterId + "/" + fileName;


            // 准备本地输出流环境
            String tempFilePath = UploadDownloadCT.DOWNLOAD_DEFAULT_PATH + "/" + fileName;
            File tempFile = new File(tempFilePath);

            // 确保父目录存在
            File parentDir = tempFile.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new ServiceException("无法创建本地目录：" + parentDir.getAbsolutePath());
            }

            // 确保文件存在
            if (!tempFile.exists() && !tempFile.createNewFile()) {
                throw new ServiceException("无法创建本地文件：" + tempFilePath);
            }

            // 获取到本地磁盘的文件 (流)
            os = getOutputStream(tempFilePath);
            boolean success = hdfsRepo.download4HDFS(filePathInHDFS, os);

            // 判断是否成功
            if (!success) {
                throw new ServiceException("下载时 HDFS 传递文件失败"); //手动抛出异常
            }

            return tempFilePath;

        } catch (Exception e) {
            throw new ServiceException("下载时 获取本地文件失败");
        } finally {
            // 关闭流
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    log.error("关闭流失败", e);
                }
            }
        }


    }

    /**
     * 定位到上面的文件对象
     * ? note: 进行本地缓存的文件对象重命名避免冲突 + 移动到下载根目录, 无需使用, 因为一是保证在对应位置无重名情况, 二是下载直接返回源文件即可
     */
    public File locateRenameFile(String fileName, String firstFileDiskPath, Long creatorUserId, Long fromClusterId) {

        // 1 获取到本地磁盘接受到的文件
        File file = new File(firstFileDiskPath);
        if (!file.exists()) {
            throw new ServiceException("下载过程系统的临时文件对象不存在");
        }

        return file;
    }

    /**
     * 响应客户
     */
    public void download2Client(File realLocalTempFile, HttpServletResponse response) {

        // 比较标准的用户下载模式代码

        // 设置响应头
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realLocalTempFile.getName(), StandardCharsets.UTF_8));

        // 设置响应内容
        try (InputStream in = new FileInputStream(realLocalTempFile); OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            throw new ServiceException(ServerError.SERVICE_ERROR);
        }

    }

    public void download2ClientBatch(List<File> fileResList, HttpServletResponse response) {

        // 1 设置 ZIP 响应头
        response.reset();
        response.setContentType("application/zip");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=\"files_" + URLEncoder.encode("用户批量下载文件打包", StandardCharsets.UTF_8) + ".zip\""
        );

        // 2 进行文件压缩处理

        // 3 打包并输出到客户端
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {

            byte[] buffer = new byte[1024];

            for (File file : fileResList) {

                ZipEntry entry = new ZipEntry(file.getName());
                zipOut.putNextEntry(entry);

                // 读取文件并写入到压缩流
                try (FileInputStream fis = new FileInputStream(file)) {
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, len);
                    }
                }

                zipOut.closeEntry();
            }

            zipOut.finish();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 下载业务中, 删除本地磁盘产生的临时文件
     * ? note: 不要下载一半就给人家删除了啊
     */
    @Async
    public void cleanTempFile(File realLocalTempFile) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            // 删除本地磁盘的临时文件
            if (realLocalTempFile.exists()) {

                boolean deleted = realLocalTempFile.delete();

                if (!deleted) {
                    log.error("删除下载中的本地磁盘的临时文件失败");
                }
            } else {
                log.warn("下载中的本地磁盘的临时文件不存在");
            }
        }, 20, java.util.concurrent.TimeUnit.SECONDS); // 20秒后删除

        // 关闭调度器
        scheduler.shutdown();
    }
}
