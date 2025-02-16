package xyz.spc.infra.feign.Guest;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "guest-app")
public interface LogsClient {
}
