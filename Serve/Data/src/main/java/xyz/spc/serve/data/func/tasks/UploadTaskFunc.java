package xyz.spc.serve.data.func.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.constant.UploadDownloadCT;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.common.util.fileUtil.UploadUtil;
import xyz.spc.domain.dos.Data.tasks.UploadTaskDO;
import xyz.spc.domain.model.Data.tasks.UploadTask;
import xyz.spc.gate.vo.Data.tasks.UploadTaskVO;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.infra.special.Data.tasks.TasksRepo;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadTaskFunc {

    /**
     * Repo
     */
    private final TasksRepo tasksRepo;
    private final HdfsRepo hdfsRepo;


    /**
     * 文件上传的中转存储
     */
    public String handleTempUpload(MultipartFile file, Long userId, Long clusterId) throws IOException {
        String basePath = UploadDownloadCT.UPLOAD_DEFAULT_PATH;

        // 查找到本机上的中转目录
        File baseDir = new File(basePath);

        // 需要创建中转目录 (递归)
        if (!baseDir.exists()) {
            boolean created = baseDir.mkdirs();
            if (created) {
                log.debug("系统上传区域中转文件夹 {} 创建成功", basePath);
            } else {
                log.error("系统上传区域中转文件夹 {} 创建失败", basePath);
                throw new ServiceException("系统上传区域中转文件夹创建失败");
            }
        }

        // 将文件存储至中转目录 并 返回存储后文件的相对路径
        String localFilePath = UploadUtil.upload(basePath, file, null);

        return localFilePath;
    }


    /**
     * 任务表的任务创建
     */
    public Long taskGen(Long fileId, String fileName, Long userId) {

        Long id = SnowflakeIdUtil.nextId();

        UploadTaskDO uploadTaskDO = UploadTaskDO.builder()
                .id(id)
                .fileId(fileId)
                .fileName(fileName)
                .pid(fileId)
                .userId(userId)
                .status(UploadTask.STATUS_NOT_START) //未开始
                .fileSizeTotal(0L)  // 文件总大小 (模拟)
                .fileSizeOk(0L) // 已完成的文件大小 (模拟)
                .executor(UploadTask.EXECUTOR_LOCAL) // 执行器 (本地)
                .build();

        tasksRepo.uploadTaskService.save(uploadTaskDO);

        return id;
    }

    /**
     * 通过 id 查具体的任务信息
     */
    public UploadTaskVO getTaskInfo(Long taskId) {

        // 获取
        UploadTaskDO uploadTaskDO = tasksRepo.uploadTaskService.getById(taskId);

        if (uploadTaskDO == null) {
            throw new ServiceException("任务不存在");
        }

        return UploadTaskVO.builder()
                .id(uploadTaskDO.getId())
                .fileId(uploadTaskDO.getFileId())
                .fileName(uploadTaskDO.getFileName())
                .pid(uploadTaskDO.getPid())
                .userId(uploadTaskDO.getUserId())
                .status(uploadTaskDO.getStatus())
                .fileSizeTotal(uploadTaskDO.getFileSizeTotal())
                .fileSizeOk(uploadTaskDO.getFileSizeOk())
                .executor(uploadTaskDO.getExecutor())
                .build();
    }
}
