package xyz.spc.serve.data.func.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.infra.special.Data.tasks.TasksRepo;

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
    public void handleTempUpload(MultipartFile file, Long userId, Long clusterId) {
    }

    /**
     * 任务表的任务创建
     */
    public Long taskGen(MultipartFile file, Long userId, Long clusterId) {

        return 1L;
    }
}
