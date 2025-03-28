package xyz.spc.infra.feign.Cluster;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;

import java.util.List;

@FeignClient(name = "cluster-app")
public interface InteractsClient {

    String BASE_URL = "/Cluster/interacts";

    /**
     * id 批量查询 Post 动态
     */
    @PostMapping(BASE_URL + "/post/batch")
    List<PostShowVO> getPostByIdBatch(@RequestBody List<Long> clusterIds);
}
