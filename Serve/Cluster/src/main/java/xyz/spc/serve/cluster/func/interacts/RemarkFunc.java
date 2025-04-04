package xyz.spc.serve.cluster.func.interacts;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.interacts.RemarkDO;
import xyz.spc.domain.model.Cluster.interacts.Remark;
import xyz.spc.infra.special.Cluster.interacts.RemarksRepo;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemarkFunc {


    /**
     * Repo
     */
    private final RemarksRepo remarksRepo;


    /**
     * 根据id批量查询群组的评论内容
     */
    public List<String> getRemark4ClusterHallShow(Long clusterId, int amount) {

        //0. clusterId 查找 type == 0 && targetId == clusterId 的所有对象, 根据点赞数进行降序排序

        //1. 对象数量控制
        List<RemarkDO> remarks = remarksRepo.remarkService.list(
                Wrappers.lambdaQuery(RemarkDO.class)
                        .eq(RemarkDO::getTargetId, clusterId)
                        .eq(RemarkDO::getType, Remark.TYPE_CLUSTER)
                        .orderByDesc(RemarkDO::getLikes)
                        .last("LIMIT " + amount)
        );


        //2. 取出前 amount 个对象的 content
        return remarks.stream()
                .map(RemarkDO::getContent)
                .collect(Collectors.toList());
    }

}
