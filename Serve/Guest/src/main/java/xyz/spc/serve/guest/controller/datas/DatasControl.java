package xyz.spc.serve.guest.controller.datas;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Data.files.FileVO;
import xyz.spc.gate.vo.Group.groups.GroupVO;
import xyz.spc.gate.vo.Group.interacts.PostVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.guest.flow.DatasFlow;

@Slf4j
@MLog
@Tag(name = "Levels", description = "用户数据合集")
@RequestMapping("/Guest/datas")
@RestController
@RequiredArgsConstructor
public class DatasControl {

    // Flow
    private final DatasFlow datasFlow;


    //! Func


    //! ADD


    //! DELETE


    //! UPDATE


    //! Query

    /**
     * 查询用户 动态 - 0 收藏信息
     */
    @GetMapping("/collect/data/post")
    @Operation(summary = "查用户 动态 收藏信息")
    @Parameter(name = "id", description = "用户id", required = true)
    public Result<PostVO> getUserDataOfPost(@NotNull @RequestParam("id") Long id) {
        return Result.success(datasFlow.getUserDataOfPost(id));
    }
    //http://localhost:10000/Guest/datas/collect/data/post?id=...


    /**
     * 查询用户 文件 - 1 收藏信息
     */
    @GetMapping("/collect/data/file")
    @Operation(summary = "查用户 文件 收藏信息")
    @Parameter(name = "id", description = "用户id", required = true)
    public Result<FileVO> getUserDataOfFile(@NotNull @RequestParam("id") Long id) {
        return Result.success(datasFlow.getUserDataOfFile(id));
    }
    //http://localhost:10000/Guest/datas/collect/data/file?id=...


    /**
     * 查询用户 群组 - 2 收藏信息
     */
    @GetMapping("/collect/data/group")
    @Operation(summary = "查用户 群组 收藏信息")
    @Parameter(name = "id", description = "用户id", required = true)
    public Result<GroupVO> getUserDataOfGroup(@NotNull @RequestParam("id") Long id) {
        return Result.success(datasFlow.getUserDataOfGroup(id));
    }
    //http://localhost:10000/Guest/datas/collect/data/group?id=...
}
