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
    private final UploadTaskRepo uploadTaskFunc;

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
    public Long taskGen(Long fileId, String fileName, Long userId, String tempFilePath) {

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
                .build();



    }

}
