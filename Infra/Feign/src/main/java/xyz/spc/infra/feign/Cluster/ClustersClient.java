package xyz.spc.infra.feign.Cluster;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "cluster-app")
public interface ClustersClient {

    String BASE_URL = "/Cluster/clusters";

    /**
     * 根据groupIds获取groupNames
     */
    @PostMapping(BASE_URL + "/getClusterNames")
    List<String> getClusterNamesByIds(@RequestBody List<Long> groupIds);
}
