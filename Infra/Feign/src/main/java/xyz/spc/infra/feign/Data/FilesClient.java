package xyz.spc.infra.feign.Data;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Data.files.FileShowVO;

@FeignClient(name = "data-app")
public interface FilesClient {

    String BASE_URL = "/Data/files";

    /**
     * 用户获取收藏分页数据 - 文件
     */
    @GetMapping(BASE_URL + "/collect/data/file")
    PageResponse<FileShowVO> getUserDataOfFile(
            @RequestParam("id") @NotNull Long id,
            @RequestParam("current") Long current,
            @RequestParam("size") Long size);
}
