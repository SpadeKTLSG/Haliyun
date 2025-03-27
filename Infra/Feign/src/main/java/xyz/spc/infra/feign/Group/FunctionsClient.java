package xyz.spc.infra.feign.Cluster;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cluster-app")
public interface FunctionsClient {
}
