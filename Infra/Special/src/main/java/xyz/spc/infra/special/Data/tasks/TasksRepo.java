package xyz.spc.infra.special.Data.tasks;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Data.tasks.DownloadTaskMapper;
import xyz.spc.infra.mapper.Data.tasks.UploadTaskMapper;
import xyz.spc.infra.repo.Data.tasks.DownloadTaskService;
import xyz.spc.infra.repo.Data.tasks.UploadTaskService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class TasksRepo {

    public final UploadTaskService uploadTaskService;
    public final UploadTaskMapper uploadTaskMapper;
    public final DownloadTaskService downloadTaskService;
    public final DownloadTaskMapper downloadTaskMapper;
}
