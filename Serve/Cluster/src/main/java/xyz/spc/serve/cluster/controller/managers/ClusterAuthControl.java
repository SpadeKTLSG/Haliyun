package xyz.spc.serve.cluster.controller.managers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.gate.vo.Cluster.managers.ClusterAuthVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.cluster.flow.ManagersFlow;

import java.util.List;

@Slf4j
@MLog
@Tag(name = "Clusters", description = "群组管理合集")
@RequestMapping("/Cluster/managers")
@RestController
@RequiredArgsConstructor
public class ClusterAuthControl {

    // Flow
    private final ManagersFlow managersFlow;


    //! Client


    //! Func


    //! ADD


    //! DELETE


    //! UPDATE


    //! Query

    /**
     * 分页查询群组中所有用户的权限 (详情也是复用这个数据)
     * 分页数据展示List: account + 对应权限 0 / 1 + status
     * 详情就做这个的美化展示
     */
    @GetMapping("/cluster_auth/list")
    public Result<List<ClusterAuthVO>> getAllClusterAuths(
            @RequestParam Long targetClusterId,

            @RequestParam("current") Integer current,
            @RequestParam("size") Integer size
    ) {
        return Result.success(managersFlow.getAllClusterAuths(targetClusterId, new PageRequest(current, size)));
    }

}
