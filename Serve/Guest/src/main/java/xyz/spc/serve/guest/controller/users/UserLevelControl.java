package xyz.spc.serve.guest.controller.users;


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
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.guest.flow.UsersFlow;

@Slf4j
@MLog
@Tag(name = "UsersLevel", description = "用户用户等级合集")
@RequestMapping("/Guest/users/userslevel")
@RestController
@RequiredArgsConstructor
public class UserLevelControl {

    // Flow
    private final UsersFlow usersFlow;

    /**
     * 查询用户等级信息
     */
    @GetMapping("/floor")
    @Operation(summary = "查用户等级层级")
    @Parameter(name = "id", description = "用户id", required = true)
    public Result<Integer> getUserInfo(@NotNull @RequestParam("id") Long id) {
        return Result.success(usersFlow.getUserLevelFloor(id));
    }
    //http://localhost:10000/Guest/users/userslevel/floor?id=...
}
