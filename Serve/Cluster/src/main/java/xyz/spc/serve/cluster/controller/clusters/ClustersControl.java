package xyz.spc.serve.cluster.controller.clusters;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
