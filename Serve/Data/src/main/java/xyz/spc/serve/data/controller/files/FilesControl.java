package xyz.spc.serve.data.controller.files;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.gate.vo.Data.files.FileShowVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.data.flow.FilesFlow;

import java.util.List;

@Slf4j
@MLog
@Tag(name = "Files", description = "文件文件合集")
@RequestMapping("/Data/files")
@RestController
@RequiredArgsConstructor
public class FilesControl {


    // Flow
    private final FilesFlow filesFlow;


    //! Client

    /**
     * id 批量查询 Post 动态
     */
    @PostMapping("/file/batch")
    List<FileShowVO> getFileByIdBatch(@RequestBody List<Long> fileIds) {
        return filesFlow.getFileByIdBatch(fileIds);
    }

    /**
     * 数据系统预热
     */
    @PostMapping("/hotting")
    Result<Object> hotDataSystem() {
        if (filesFlow.tryAcquireDataSystem()) {
            return Result.success("HDFS / Data 模块正常运行中!");
        } else {
            return Result.fail("HDFS / Data 模块正在维护中, 请稍后再试!");
        }
    }

    //! Func

    //! ADD


    //! DELETE

    /**
     * 删除文件
     */
    @DeleteMapping("/file/delete")
    @Operation(summary = "删除文件")
    public Result<Object> deleteFile(
            @RequestParam("fileId") Long fileId
    ) {
        filesFlow.deleteFile(fileId);
        return Result.success();
    }
    //http://localhost:10000/Data/files/file/delete?fileId=...&clusterId=...&userId=...


    //! UPDATE


    //! Query

    /**
     * 分页获取群组中的文件列表
     */
    @GetMapping("/file/group_files")
    @Operation(summary = "分页查群组内的文件列表")
    public Result<PageResponse<FileGreatVO>> getGroupFilePage(

            @RequestParam("clusterId") Long clusterId,
            @RequestParam("current") Integer current,
            @RequestParam("size") Integer size
    ) {
        return Result.success(filesFlow.getGroupFilePage(clusterId, new PageRequest(current, size)));
    }
    //http://localhost:10000/Data/files/file/group_files?clusterId=...&current=1&size=10


    /**
     * 分页获取群组中的文件列表, 按照文件名进行模糊查询
     */
    @GetMapping("/file/group_files/name")
    public Result<PageResponse<FileGreatVO>> getGroupFilePage8Name(

            @RequestParam("clusterId") Long clusterId,
            @RequestParam("fileName") Long fileName,
            @RequestParam("current") Integer current,
            @RequestParam("size") Integer size

    ) {
        return Result.success(filesFlow.getGroupFilePage8Name(clusterId, fileName, new PageRequest(current, size)));
    }


}
