package xyz.spc.infra.feign.Data;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "data-app")
public interface TasksClient {
}
