package xyz.spc.serve.group.flow.interacts;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Group.interacts.PostDO;
import xyz.spc.gate.vo.Group.interacts.PostShowVO;
import xyz.spc.serve.group.func.interacts.PostFunc;
import xyz.spc.serve.group.func.interacts.RemarkFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractsFlow {


    //Feign


    //Func
    private final PostFunc postFunc;
    private final RemarkFunc remarkFunc;


    /**
     * 用户获取收藏分页数据 - Post动态
     */
    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        PageResponse<PostDO> tempPage = postFunc.getUserDataOfPost(id, pageRequest);
        //
    }
}
