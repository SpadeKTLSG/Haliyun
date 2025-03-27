package xyz.spc.serve.group.flow.interacts;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Group.interacts.PostDO;
import xyz.spc.gate.vo.Group.interacts.PostShowVO;
import xyz.spc.serve.group.func.groups.GroupsFunc;
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
    private final GroupsFunc groupsFunc;


    /**
     * 用户获取收藏分页数据 - Post动态
     */
    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        //先获取存储的动态数据
        PageResponse<PostDO> tempPage = postFunc.getUserDataOfPost(id, pageRequest);

        //抽取Groupids, 拿去Group获取 GroupName 字段填充
        List<Long> groupIds = new ArrayList<>();
        List<String> groupName = groupsFunc.getGroupNamesByIds(groupIds);

        //从tempPage获取数据, 拿去处理为PostShowVO
        List<PostShowVO> postShowVOS = new ArrayList<>();
        for (PostDO postDO : tempPage.getRecords()) {
            PostShowVO postShowVO = PostShowVO.builder()
                    //处理限定于收藏分页的需要展示字段: id, groupName, title
                    .id(postDO.getId())
                    .groupName(groupName.get(groupIds.indexOf(postDO.getGroupId())))
                    .title(postDO.getTitle())
                    .build();
            postShowVOS.add(postShowVO);
        }

        return new PageResponse<>(tempPage.getCurrent(), tempPage.getSize(), tempPage.getTotal(), postShowVOS);
    }
}
