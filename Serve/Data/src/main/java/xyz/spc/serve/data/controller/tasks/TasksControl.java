package xyz.spc.serve.data.controller.tasks;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.funcpack.Result;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.data.flow.TasksFlow;

@Slf4j
@MLog
@Tag(name = "Tasks", description = "文件任务合集")
@RequestMapping("/Data/tasks")
@RestController
@RequiredArgsConstructor
public class TasksControl {


    private final TasksFlow tasksFlow;

    /**
     * 上传文件接口 (无网关/MVC鉴权限制)
     * 将文件先存储到本机中转区
     */
    @PostMapping("/upload/file")
    public Result<Object> uploadFile(
            @RequestParam("file") MultipartFile file, //上传文件对象
            @RequestParam("userId") Long userId,  // 用户id 由于没有登录鉴权, 不得不直接传入
            @RequestParam("clusterId") Long clusterId,
            @RequestParam(value = "pid", defaultValue = "0") Long pid // 未来拟合的需求: 在页面的对应位置点击了上传之后, 应该设置对应的pid为文件夹的id
            //? note: 关于树形结构的废案, 详情见个人笔记
    ) {

        // pid 字段忽略掉
        tasksFlow.uploadFile(file, clusterId, userId);
        return Result.success();
    }

}
