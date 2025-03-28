package xyz.spc.serve.cluster.flow;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Cluster.interacts.PostDO;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;
import xyz.spc.serve.cluster.func.clusters.ClustersFunc;
import xyz.spc.serve.cluster.func.interacts.PostFunc;
import xyz.spc.serve.cluster.func.interacts.RemarkFunc;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractsFlow {


    //Feign


    //Func
    private final PostFunc postFunc;
    private final RemarkFunc remarkFunc;
    private final ClustersFunc clustersFunc;

    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        PageResponse<PostDO> tmp = postFunc.getUserDataOfPost(id, pageRequest);


        PageResponse<PostShowVO> res = new PageResponse<>(
                tmp.getCurrent(),
                tmp.getSize(),
                tmp.getTotal()
        );

        //补充信息
        for (PostDO post : tmp.getRecords()) {
            PostShowVO postShowVO = new PostShowVO();
            postShowVO.setId(post.getId());
            postShowVO.setUserId(post.getUserId());
            postShowVO.setContent(post.getContent());
            postShowVO.setCreateTime(post.getCreateTime());
            postShowVO.setUpdateTime(post.getUpdateTime());
            postShowVO.setTitle(post.getTitle());
            postShowVO.setPics(post.getPics());
            postShowVO.setPersonShow(post.getPersonShow());
            //这个要改一次性批量调用
            postShowVO.setClusterName(clustersFunc.getClusterNamesByIds(List.of(post.getClusterId())).get(0));
            res.getRecords().add(postShowVO);
        }

        return res;
    }
}
