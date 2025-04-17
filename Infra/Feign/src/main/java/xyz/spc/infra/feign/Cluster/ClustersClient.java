package xyz.spc.infra.feign.Cluster;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;

import java.util.List;

@FeignClient(name = "cluster-app")
public interface ClustersClient {

    String BASE_URL = "/Cluster/clusters";

    /**
     * 根据groupIds获取groupNames
     */
    @PostMapping(BASE_URL + "/getClusterNames")
    List<String> getClusterNamesByIds(@RequestBody List<Long> groupIds);


    /**
     * id 批量查询群组
     */
    @PostMapping(BASE_URL + "/cluster/batch")
    List<ClusterVO> getClusterByIdBatch(@RequestBody List<Long> pagedClusterIds);


    /**
     * 判断群组创建者是否是这个用户
     */
    @GetMapping(BASE_URL + "/cluster/creator/check")
    boolean checkClusterCreatorEqual(@RequestParam Long clusterId, @RequestParam Long myUserId);

    /**
     * 判断对应群组人满了没有
     */
    @GetMapping(BASE_URL + "/cluster/full/check")
    boolean checkClusterFull(@RequestParam Long clusterId);

    /**
     * 操作对应群组人数 +=1
     */
    @PutMapping(BASE_URL + "/cluster/user/count")
    void opClusterUserCount(Long clusterId, String opType, int amount);
}
