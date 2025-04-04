package xyz.spc.serve.cluster.controller.clusters;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.dto.Cluster.clusters.ClusterDTO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterGreatVO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.cluster.flow.ClustersFlow;

import java.util.List;


@Slf4j
@MLog
@Tag(name = "Clusters", description = "群组群组合集")
@RequestMapping("/Cluster/clusters")
@RestController
@RequiredArgsConstructor
public class ClustersControl {

    // Flow
    private final ClustersFlow clustersFlow;


    //! Client

    /**
     * 根据groupIds获取groupNames
     */
    @PostMapping("/getClusterNames")
    List<String> getClusterNamesByIds(@RequestBody List<Long> groupIds) {
        return clustersFlow.getClusterNamesByIds(groupIds);
    }

    /**
     * id 批量查询群组
     */
    @PostMapping("/cluster/batch")
    List<ClusterVO> getClusterByIdBatch(@RequestBody List<Long> pagedClusterIds) {
        return clustersFlow.getClusterByIdBatch(pagedClusterIds);
    }

    //! Func


    //! DELETE

    /**
     * 小院删除群组
     */
    @DeleteMapping("/delete")
    Result<Object> deleteCluster(@RequestParam Long clusterId) {
        clustersFlow.deleteCluster(clusterId);
        return Result.success();
    }
    //http://localhost:10000/Cluster/clusters/delete?clusterId=...


    //! ADD

    /**
     * 小院创建群组 (就传递两个名称 name,nickname)
     */
    @PostMapping("create")
    Result<Object> createCluster(@RequestBody ClusterDTO clusterDTO) {
        clustersFlow.createCluster(clusterDTO);
        return Result.success();
    }
    //http://localhost:10000/Cluster/clusters/create

    //! UPDATE


    //! Query

    /**
     * 大厅群组分页查询 (查所有群组)
     */
    @GetMapping("/hall/all")
    Result<PageResponse<ClusterVO>> getHallClusters(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return Result.success(clustersFlow.getHallClusters(new PageRequest(current, size)));
    }
    //http://localhost:10000/Cluster/clusters/hall/all?page=1&size=10

    /**
     * 小院群组分页查询 (查自己加入的群组)
     */
    @GetMapping("/yard/all")
    Result<PageResponse<ClusterVO>> getYardClusters(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return Result.success(clustersFlow.getYardClusters(new PageRequest(current, size)));
    }
    //http://localhost:10000/Cluster/clusters/yard/all?page=1&size=10

    /**
     * id 查单个群组全部信息
     */
    @GetMapping("/hall/one")
    Result<ClusterGreatVO> getAllClusterById(@RequestParam Long id) {
        return Result.success(clustersFlow.getAllClusterById(id));
    }
    //http://localhost:10000/Cluster/clusters/hall/one?id=...
}
