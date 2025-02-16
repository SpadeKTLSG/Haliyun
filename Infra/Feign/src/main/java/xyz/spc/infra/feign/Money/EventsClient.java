package xyz.spc.infra.feign.Money;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "money-app")
public interface EventsClient {
}
