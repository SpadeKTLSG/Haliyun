package xyz.spc.serve.data.controller.files;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
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

        return Result.success("HDFS / Data 模块正常运行中!");
    }

}
