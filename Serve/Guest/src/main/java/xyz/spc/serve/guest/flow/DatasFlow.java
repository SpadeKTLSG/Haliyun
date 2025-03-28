package xyz.spc.serve.guest.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Guest.datas.CollectDO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;
import xyz.spc.gate.vo.Data.files.FileShowVO;
import xyz.spc.gate.vo.Guest.datas.CollectCountVO;
import xyz.spc.infra.feign.Cluster.InteractsClient;
import xyz.spc.infra.feign.Data.FilesClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.guest.func.datas.CollectFunc;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasFlow {

    //Feign
    private final InteractsClient interactsClient;
    private final FilesClient filesClient;

    //Func
    private final CollectFunc collectFunc;

    //! Client

    public CollectCountVO getUserDataOfAllCollect() {
        return null;
    }


    public PageResponse<PostShowVO> getUserDataOfPost(PageRequest pageRequest) {

        //? note 之后也是像这样, 哪个区域的业务, 就主要由哪个区域的Flow来进行编排处理, 哪怕需要涉及到多个调用 (因为你需要去处理外挂的逻辑, 就很Dirty. Feign的调用在这里性能忽略掉.

        // 这边的链路是这样的:
        //
        //1. 前端用户需要先查自己的 收藏表, 得到具体收藏了哪些东西, 把对应的东西取出来, 作为去获取的清单.
        //2. 用清单去对应的Feign进行处理, 具体就是用 对应物品的 id + 用户 id 进行where查询, 之后把这个查出来的所有数据返回回去
        //3. 前端展示数据.

        Long userId = Objects.requireNonNull(UserContext.getUI());

        // 获取这个用户收藏的动态列表 (id 用 TL来做的)
        List<CollectDO> collectList = collectFunc.getUserCollectListOfPost(userId);

        // 收集所有需要查询的 clusterId
        List<Long> clusterIds = collectList.stream()
                .map(CollectDO::getId)
                .toList();


        // 批量查询 clusterName
        List<String> clusterNames = interactsClient.getClusterNamesByIds(clusterIds);


        // 发起远程调用, 对每一个收藏进行补偿查询
        return interactsClient.getAllDataOfPost(collectList, pageRequest.getCurrent(), pageRequest.getSize());
    }


    public PageResponse<FileShowVO> getUserDataOfFile(PageRequest pageRequest) {
        //获取这个用户收藏的动态列表 (id 用 TL来做的)
        Long userId = Objects.requireNonNull(UserContext.getUI());

        return filesClient.getUserDataOfFile(userId, pageRequest.getCurrent(), pageRequest.getSize());
    }

    public PageResponse<ClusterVO> getUserDataOfCluster(PageRequest pageRequest) {
        Long userId = Objects.requireNonNull(UserContext.getUI());
        return new PageResponse<>(10, 10, 10, null);
    }

}
