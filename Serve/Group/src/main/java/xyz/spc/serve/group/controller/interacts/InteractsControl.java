package xyz.spc.serve.group.controller.interacts;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Group.interacts.PostShowVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.group.flow.interacts.InteractsFlow;

@Slf4j
@MLog
@Tag(name = "Post", description = "群组互动合集")
@RequestMapping("/Group/interacts")
@RestController
@RequiredArgsConstructor
public class InteractsControl {

    // Flow
    private final InteractsFlow interactsFlow;

    //! Func


    //! ADD


    //! DELETE


    //! UPDATE


    //! Query


    //! Client


    /**
     * 用户获取收藏分页数据 - Post动态
     */
    @GetMapping("/collect/data/post")
    PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        return interactsFlow.getUserDataOfPost(id, pageRequest);
    }

}
