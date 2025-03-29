package xyz.spc.serve.guest.func.datas;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.datas.CollectDO;
import xyz.spc.domain.model.Guest.datas.Collect;
import xyz.spc.infra.special.Guest.datas.CollectsRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectFunc {

    /**
     * Repo
     */
    private final CollectsRepo collectsRepo;


    /**
     * 查询用户收藏的动态列表
     */
    public List<CollectDO> getUserCollectListOfPost(Long userId) {


        List<CollectDO> tmp = collectsRepo.collectService.list(Wrappers.lambdaQuery(CollectDO.class)
                .eq(CollectDO::getUserId, userId)
                .eq(CollectDO::getType, Collect.TYPE_POST)
        );

        return tmp;

    }

    /**
     * 查询用户收藏的文件列表
     */
    public List<CollectDO> getUserCollectListOfFile(Long userId) {

        List<CollectDO> tmp = collectsRepo.collectService.list(Wrappers.lambdaQuery(CollectDO.class)
                .eq(CollectDO::getUserId, userId)
                .eq(CollectDO::getType, Collect.TYPE_FILE)
        );

        return tmp;
    }

    /**
     * 查询用户收藏的群组列表
     */
    public List<CollectDO> getUserCollectListOfCluster(Long userId) {

        List<CollectDO> tmp = collectsRepo.collectService.list(Wrappers.lambdaQuery(CollectDO.class)
                .eq(CollectDO::getUserId, userId)
                .eq(CollectDO::getType, Collect.TYPE_CLUSTER)
        );

        return tmp;
    }
}
