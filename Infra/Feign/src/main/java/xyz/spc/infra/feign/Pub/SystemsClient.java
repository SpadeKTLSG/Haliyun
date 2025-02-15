package xyz.spc.infra.feign.Pub;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pub-app")
public interface SystemsClient {
}
