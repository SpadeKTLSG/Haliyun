package xyz.spc.serve.cluster.controller.interacts;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.cluster.flow.InteractsFlow;

import java.util.List;


@Slf4j
@MLog
@Tag(name = "Post", description = "群组互动合集")
@RequestMapping("/Cluster/interacts")
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
    @PostMapping("/post/batch")
    List<PostShowVO> getPostByIdBatch(@RequestBody List<Long> postIds) {
        return interactsFlow.getPostByIdBatch(postIds);
    }


}
