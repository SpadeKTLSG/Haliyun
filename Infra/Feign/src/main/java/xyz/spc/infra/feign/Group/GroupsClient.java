package xyz.spc.infra.feign.Cluster;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "group-app")
public interface ClustersClient {


}
