package xyz.spc.infra.feign.Data;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "data-app")
public interface FilesClient {

    String BASE_URL = "/Data/files";


}
