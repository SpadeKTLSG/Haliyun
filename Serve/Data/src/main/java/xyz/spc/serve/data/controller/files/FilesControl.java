package xyz.spc.serve.data.controller.files;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Data.files.FileShowVO;
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
    @GetMapping("/collect/data/file")
    PageResponse<FileShowVO> getUserDataOfFile(
            @RequestParam("id") @NotNull Long id,
            @RequestParam("current") Long current,
            @RequestParam("size") Long size) {
        filesFlow.getUserDataOfFile(id, current, size);
    }

}
