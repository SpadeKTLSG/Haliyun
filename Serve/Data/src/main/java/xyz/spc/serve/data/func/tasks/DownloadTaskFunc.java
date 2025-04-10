package xyz.spc.serve.data.func.tasks;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.common.constant.UploadDownloadCT;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Data.tasks.DownloadTaskDO;
import xyz.spc.domain.model.Data.tasks.DownloadTask;
import xyz.spc.gate.vo.Data.tasks.DownloadTaskVO;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.infra.special.Data.tasks.TasksRepo;

import java.io.OutputStream;

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

        DownloadTaskDO downTaskDO = DownloadTaskDO.builder()
                .id(id)
                .fileId(fileId)
                .fileName(fileName)
                .pid(fileId)
                .userId(creatortUserId)
                .status(DownloadTask.STATUS_NOT_START) //未开始
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

        return DownloadTaskVO.builder()
                .id(downloadTaskDO.getId())
                .fileId(downloadTaskDO.getFileId())
                .fileName(downloadTaskDO.getFileName())
                .pid(downloadTaskDO.getPid())
                .userId(downloadTaskDO.getUserId())
                .status(downloadTaskDO.getStatus())
                .fileSizeTotal(downloadTaskDO.getFileSizeTotal())
                .fileSizeOk(downloadTaskDO.getFileSizeOk())
                .executor(downloadTaskDO.getExecutor())
                .build();
    }

    /**
     * 下载任务开始
     */
    public void startTask(Long taskId) {

        tasksRepo.downloadTaskService.update(Wrappers.lambdaUpdate(DownloadTaskDO.class)
                .set(DownloadTaskDO::getStatus, DownloadTask.STATUS_RUNNING) // 运行中
                .eq(DownloadTaskDO::getId, taskId) // 任务id
                .eq(DownloadTaskDO::getExecutor, DownloadTask.EXECUTOR_LOCAL) // note: 绑定对应执行机器, 实现唯一对应. 本地执行器
        );

    }

    /**
     * 下载任务完成
     */
    public void completeTask(Long taskId) {

        tasksRepo.downloadTaskService.update(Wrappers.lambdaUpdate(DownloadTaskDO.class)
                .set(DownloadTaskDO::getStatus, DownloadTask.STATUS_FINISH) // 完成
                .eq(DownloadTaskDO::getId, taskId) // 任务id
                .eq(DownloadTaskDO::getExecutor, DownloadTask.EXECUTOR_LOCAL) // note: 绑定对应执行机器, 实现唯一对应. 本地执行器
        );
    }

    /**
     * 登记失败任务
     */
    @Async
    public void failTask(Long taskId) {

        tasksRepo.downloadTaskService.update(Wrappers.lambdaUpdate(DownloadTaskDO.class)
                .set(DownloadTaskDO::getStatus, DownloadTask.STATUS_FAIL) // 失败
                .eq(DownloadTaskDO::getId, taskId) // 任务id
                .eq(DownloadTaskDO::getExecutor, DownloadTask.EXECUTOR_LOCAL) // note: 绑定对应执行机器, 实现唯一对应. 本地执行器
        );
    }


    /**
     * HDFS 下载到本地 Temp 文件
     */
    public String handleTempDownload(String fileName, Long creatorUserId, Long fromClusterId) {

        String filePathInHDFS = "/"; // HDFS 文件路径
        filePathInHDFS = filePathInHDFS + creatorUserId + "/" + fromClusterId + "/" + fileName;

        // 准备本地输出流环境
        String basePath = UploadDownloadCT.DOWNLOAD_DEFAULT_PATH;
        String tempFilePath = basePath + fileName;
        OutputStream os;

        boolean success = hdfsRepo.download4HDFS(filePathInHDFS

        if (!success) {
            throw new ServiceException("上传 HDFS 失败"); //手动抛出异常
        }
    }
}
