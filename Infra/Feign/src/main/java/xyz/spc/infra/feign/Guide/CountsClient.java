package xyz.spc.infra.feign.Guide;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "guide-app")
public interface CountsClient {
}
