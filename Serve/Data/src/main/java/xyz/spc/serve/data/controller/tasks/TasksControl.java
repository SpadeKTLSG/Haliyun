package xyz.spc.serve.data.controller.tasks;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.data.flow.TasksFlow;

@Slf4j
@MLog
@Tag(name = "Tasks", description = "文件任务合集")
@RequestMapping("/Data/tasks")
@RestController
@RequiredArgsConstructor
public class TasksControl {

    // Flow
    private final TasksFlow tasksFlow;


    //! Func

    /**
     * 上传文件接口
     * ? note: 上传文件接口 无网关/MVC鉴权限制
     */
    @PostMapping("/upload/file")
    public Result<Object> uploadFile(
            @RequestParam("file") @NonNull MultipartFile file, //上传文件对象
            @RequestParam("userId") @NonNull Long userId,  // 用户id 由于没有登录鉴权, 不得不直接传入
            @RequestParam("clusterId") @NonNull Long clusterId,
            @RequestParam(value = "pid", defaultValue = "0") Long pid // 未来拟合的需求: 在页面的对应位置点击了上传之后, 应该设置对应的pid为文件夹的id
            //? note: 关于树形结构的废案, 详情见个人笔记
    ) {

        // pid 字段忽略掉
        try {
            tasksFlow.uploadFile(file, clusterId, userId);
        } catch (Exception e) {
            throw new ServiceException(ServerError.SERVICE_RESOURCE_ERROR);
        }

        return Result.success();
    }


    /**
     * 下载文件接口
     */
    @GetMapping("/download/file")
    public Result<Object> downloadFile(
            @RequestParam("id") Long fileId, // 从前端查出的需要的文件对象 id
            @RequestParam("userId") Long userId,
            @RequestParam("clusterId") Long clusterId,
            HttpServletResponse response
    ) {
        try {
            tasksFlow.downloadFile(fileId, userId, clusterId, response);
        } catch (Exception e) {
            throw new ServiceException(ServerError.SERVICE_RESOURCE_ERROR);
        }

        return Result.success();
    }


    //! ADD


    //! DELETE


    //! UPDATE


    //! Query
}
