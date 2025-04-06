package xyz.spc.serve.cluster.controller.functions;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Cluster.functions.NoticeVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.cluster.flow.FunctionsFlow;

@Slf4j
@MLog
@Tag(name = "Clusters", description = "群组功能合集")
@RequestMapping("/Cluster/functions")
@RestController
@RequiredArgsConstructor
public class NoticeControl {

    // Flow
    private final FunctionsFlow functionsFlow;


    //! Client


    //! Func


    //! ADD


    //! DELETE


    //! UPDATE


    //! Query

    /**
     * 根据 群组 clusterId 获取群的公告
     */
    @GetMapping("/notice/getNotice")
    public Result<NoticeVO> getNotice(@RequestParam Long clusterId) {
        return Result.success(functionsFlow.getNotice(clusterId));
    }
    //http://localhost:10000/Cluster/functions/notice/getNotice?clusterId=1

}
