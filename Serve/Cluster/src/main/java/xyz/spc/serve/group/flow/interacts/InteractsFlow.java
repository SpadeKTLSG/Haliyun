package xyz.spc.serve.group.flow.interacts;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Cluster.interacts.PostDO;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;
import xyz.spc.serve.group.func.groups.ClustersFunc;
import xyz.spc.serve.group.func.interacts.PostFunc;
import xyz.spc.serve.group.func.interacts.RemarkFunc;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractsFlow {


    //Feign


    //Func
    private final PostFunc postFunc;
    private final RemarkFunc remarkFunc;
    private final ClustersFunc groupsFunc;


    /**
     * 用户获取收藏分页数据 - Post动态
     */
    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        //先获取存储的动态数据
        PageResponse<PostDO> tempPage = postFunc.getUserDataOfPost(id, pageRequest);

        //抽取Clusterids, 拿去Cluster获取 ClusterName 字段填充
        List<Long> groupIds = new ArrayList<>();
        for (PostDO postDO : tempPage.getRecords()) {
            groupIds.add(postDO.getClusterId());
        }
        List<String> groupName = groupsFunc.getClusterNamesByIds(groupIds);


        //todo 找不到对应的groupIds, 无法获取groupName, 无法填充的降级

        //从tempPage获取数据, 拿去处理为PostShowVO
        List<PostShowVO> postShowVOS = new ArrayList<>();
        for (PostDO postDO : tempPage.getRecords()) {
            int i = groupIds.indexOf(postDO.getClusterId());
            String tName = "";
            if (i == -1) {
                log.warn("未找到对应的groupIds: {}", postDO.getClusterId());
                tName = "走丢了";
            } else {
                tName = groupName.get(i);
            }
            PostShowVO postShowVO = PostShowVO.builder()
                    //处理限定于收藏分页的需要展示字段: id, groupName, title
                    .id(postDO.getId())
                    .groupName(tName)
                    .title(postDO.getTitle())
                    .build();
            postShowVOS.add(postShowVO);
        }

        return new PageResponse<>(tempPage.getCurrent(), tempPage.getSize(), tempPage.getTotal(), postShowVOS);
    }
}
