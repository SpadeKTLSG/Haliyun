package xyz.spc.serve.cluster.controller.functions;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.dto.Cluster.functions.NoticeDTO;
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

    /**
     * 添加群公告
     */
    @PostMapping("/notice/{clusterId}")
    public Result<Object> addNotice(@RequestBody NoticeDTO noticeDTO, @PathVariable Long clusterId) {
        functionsFlow.addNotice(clusterId, noticeDTO);
        return Result.success();
    }
    //http://localhost:10000/Cluster/functions/notice


    //! DELETE

    /**
     * 删除群公告
     */
    @DeleteMapping("/notice")
    public Result<Object> deleteNotice(@RequestParam Long noticeId) {
        functionsFlow.deleteNotice(noticeId);
        return Result.success();
    }
    //http://localhost:10000/Cluster/functions/notice?noticeId=1


    //! UPDATE

    /**
     * 更新群公告
     */
    @PutMapping("/notice")
    public Result<Object> updateNotice(@RequestBody NoticeDTO noticeDTO) {
        functionsFlow.updateNotice(noticeDTO);
        return Result.success();
    }
    //http://localhost:10000/Cluster/functions/notice


    //! Query

    /**
     * 根据 群组 clusterId 获取群的公告
     */
    @GetMapping("/notice")
    public Result<NoticeVO> getNotice(@RequestParam Long clusterId) {
        return Result.success(functionsFlow.getNotice(clusterId));
    }
    //http://localhost:10000/Cluster/functions/notice/?clusterId=1

}
