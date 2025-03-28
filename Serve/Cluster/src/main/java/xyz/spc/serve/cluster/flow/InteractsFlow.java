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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractsFlow {


    //Feign


    //Func
    private final PostFunc postFunc;
    private final RemarkFunc remarkFunc;
    private final ClustersFunc clustersFunc;

    /**
     * 用户获取收藏分页数据 - Post动态
     */
    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {

        //通过对应的
        PageResponse<PostDO> tmp = postFunc.getUserDataOfPost(id, pageRequest);


        PageResponse<PostShowVO> res = new PageResponse<>(
                tmp.getCurrent(),
                tmp.getSize(),
                tmp.getTotal()
        );


        // 收集所有需要查询的 clusterId
        List<Long> clusterIds = tmp.getRecords().stream()
                .map(PostDO::getClusterId)
                .collect(Collectors.toList());

        // 批量查询 clusterName
        List<String> clusterNames = clustersFunc.getClusterNamesByIds(clusterIds);

        // 补充信息
        for (int i = 0; i < tmp.getRecords().size(); i++) {
            PostShowVO postShowVO = getPostShowVO(tmp, i, clusterNames);
            res.getRecords().add(postShowVO);
        }

        return res;
    }


    /**
     * 补充 PostShowVO 信息
     */
    private static PostShowVO getPostShowVO(PageResponse<PostDO> tmp, int i, List<String> clusterNames) {
        PostDO post = tmp.getRecords().get(i);

        PostShowVO postShowVO = new PostShowVO();
        postShowVO.setId(post.getId());
        postShowVO.setUserId(post.getUserId());
        postShowVO.setContent(post.getContent());
        postShowVO.setCreateTime(post.getCreateTime());
        postShowVO.setUpdateTime(post.getUpdateTime());
        postShowVO.setTitle(post.getTitle());
        postShowVO.setPics(post.getPics());
        postShowVO.setPersonShow(post.getPersonShow());
        postShowVO.setClusterName(clusterNames.get(i));

        return postShowVO;
    }
}
