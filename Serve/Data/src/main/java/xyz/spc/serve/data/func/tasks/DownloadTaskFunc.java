package xyz.spc.serve.data.func.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.special.Data.tasks.TasksRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadTaskFunc {

    /**
     * Repo
     */
    private final TasksRepo tasksRepo;


}
