package xyz.spc.serve.cluster.func.interacts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.interacts.PostDO;
import xyz.spc.infra.special.Cluster.interacts.PostsRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostFunc {


    /**
     * Repo
     */
    private final PostsRepo postsRepo;


    /**
     * 根据id查询单条动态
     */
    public PostDO getPostById(Long id) {
        return postsRepo.postsService.getById(id);
    }


    /**
     * 根据id删除单条动态
     */
    public void deletePostById(Long id) {
        postsRepo.postsService.removeById(id);
    }

    /**
     * 根据ids批量查询动态
     */
    public List<PostDO> getPostByIdBatch(List<Long> clusterIds) {
        if (clusterIds == null || clusterIds.isEmpty()) {
            return List.of();
        }
        return postsRepo.postsMapper.selectBatchIds(clusterIds);
    }


}
