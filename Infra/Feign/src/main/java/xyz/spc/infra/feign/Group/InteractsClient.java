package xyz.spc.infra.feign.Group;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "group-app")
public interface InteractsClient {
}
