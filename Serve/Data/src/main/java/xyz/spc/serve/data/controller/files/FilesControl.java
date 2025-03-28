package xyz.spc.serve.data.controller.files;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.data.flow.FilesFlow;

@Slf4j
@MLog
@Tag(name = "Files", description = "文件文件合集")
@RequestMapping("/Data/files")
@RestController
@RequiredArgsConstructor
public class FilesControl {


    // Flow
    private final FilesFlow filesFlow;


    /**
     * 用户获取收藏分页数据 - 文件
     */

}
