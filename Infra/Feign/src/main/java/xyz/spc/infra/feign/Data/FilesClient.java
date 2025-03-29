package xyz.spc.infra.feign.Data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.spc.gate.vo.Data.files.FileShowVO;

import java.util.List;

@FeignClient(name = "data-app")
public interface FilesClient {

    String BASE_URL = "/Data/files";


    /**
     * id 批量查询 File 文件
     */
    @PostMapping(BASE_URL + "/file/batch")
    List<FileShowVO> getFileByIdBatch(@RequestBody List<Long> fileIds);
}
