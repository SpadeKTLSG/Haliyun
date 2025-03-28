package xyz.spc.serve.cluster.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
     * 根据id批量查询动态
     */
    public List<PostShowVO> getPostByIdBatch(List<Long> postIds) {

        // 批量查询动态基本存储对象
        List<PostDO> postList = postFunc.getPostByIdBatch(postIds);

        if(postList == null || postList.isEmpty()) {
            return List.of();
        }

        // 补充群组查询信息
        // 收集所有需要查询的 clusterId
        List<Long> groupIds = postList.stream()
                .map(PostDO::getClusterId)
                .collect(Collectors.toList());

        // 批量查询 clusterName
        List<String> clusterNames = clustersFunc.getClusterNamesByIds(groupIds);

        // 补充信息
        return postList.stream()
                .map(post -> {
                    PostShowVO postShowVO = new PostShowVO();
                    postShowVO.setId(post.getId());
                    postShowVO.setUserId(post.getUserId());
                    postShowVO.setContent(post.getContent());
                    postShowVO.setCreateTime(post.getCreateTime());
                    postShowVO.setUpdateTime(post.getUpdateTime());
                    postShowVO.setTitle(post.getTitle());
                    postShowVO.setPics(post.getPics());
                    postShowVO.setPersonShow(post.getPersonShow());
                    postShowVO.setClusterName(clusterNames.get(0));
                    return postShowVO;
                })
                .collect(Collectors.toList());
    }


}
