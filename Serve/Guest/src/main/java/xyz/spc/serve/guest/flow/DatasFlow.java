package xyz.spc.serve.guest.flow;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;
import xyz.spc.gate.vo.Data.files.FileShowVO;
import xyz.spc.gate.vo.Guest.datas.CollectCountVO;
import xyz.spc.infra.feign.Cluster.InteractsClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasFlow {

    //Feign
    private final InteractsClient interactsClient;


    //Func


    //! Client

    public CollectCountVO getUserDataOfAllCollect(@NotNull Long id) {
        return null;
    }

    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        return interactsClient.getUserDataOfPost(id, pageRequest.getCurrent(), pageRequest.getSize());
    }


    public PageResponse<FileShowVO> getUserDataOfFile(@NotNull Long id, PageRequest pageRequest) {
        return new PageResponse<>(10, 10, 10, null);
    }

    public PageResponse<ClusterVO> getUserDataOfCluster(@NotNull Long id, PageRequest pageRequest) {
        return new PageResponse<>(10, 10, 10, null);
    }

}
