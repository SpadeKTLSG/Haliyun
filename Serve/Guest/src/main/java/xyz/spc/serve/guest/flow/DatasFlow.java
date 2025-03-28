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

        Long userId = Objects.requireNonNull(UserContext.getUI());

        // 获取这个用户收藏的动态列表 (id 用 TL来做的)
        List<CollectDO> collectList = collectFunc.getUserCollectListOfPost(userId);


        // 发起远程调用, 对每一个收藏进行补偿查询 todo
        return interactsClient.getUserDataOfPost(userId, pageRequest.getCurrent(), pageRequest.getSize());
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
