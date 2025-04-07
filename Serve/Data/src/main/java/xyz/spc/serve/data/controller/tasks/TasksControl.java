package xyz.spc.serve.data.controller.tasks;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;

@Slf4j
@MLog
@Tag(name = "Tasks", description = "文件任务合集")
@RequestMapping("/Data/tasks")
@RestController
@RequiredArgsConstructor
public class TasksControl {



//    upload/file


}
