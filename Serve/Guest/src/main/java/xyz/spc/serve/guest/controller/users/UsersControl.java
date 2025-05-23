package xyz.spc.serve.guest.controller.users;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.constant.Guest.UsersValiClusters;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.validate.Xss;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.gate.vo.Guest.users.UserGreatVO;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.auxiliary.config.ratelimit.LimitTypeEnum;
import xyz.spc.serve.auxiliary.config.ratelimit.RateLimiter;
import xyz.spc.serve.auxiliary.config.senti.CustomBlockHandler;
import xyz.spc.serve.auxiliary.config.senti.SentinelPath;
import xyz.spc.serve.guest.flow.UsersFlow;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@MLog
@Tag(name = "Users", description = "用户用户合集")
@RequestMapping("/Guest/users")
@RestController
@RequiredArgsConstructor
public class UsersControl {

    // Flow
    private final UsersFlow usersFlow;
    private final RedisTemplate<Object, Object> redisTemplate;


    //! Client

    @GetMapping("/user_map")
    public Map<Object, Object> getUserMap(@RequestParam("tokenKey") String tokenKey) {
        return redisTemplate.opsForHash().entries(tokenKey);
    }
    //http://localhost:10000/Guest/users/user_map


    @Deprecated
    @GetMapping("/user_tl")
    public Result<Object> getUserMap() {
        return Result.success(UserContext.getUser());
    }
    //http://localhost:10000/Guest/users/user_tl


    /**
     * 获取来进行信息存储
     * <极度热点, 暴露功能>
     */
    @GetMapping("/user_mark")
    public Result<Map<String, String>> getUserMark(@RequestParam String account) {
        return Result.success(usersFlow.getUserMark(account));
    }
    //http://localhost:10000/Guest/users/user_mark


    /**
     * 查用户加入的群组id清单
     */
    @GetMapping("/user_clusters")
    public Result<List<Long>> getUserClusterIds() {
        return Result.success(usersFlow.getUserClusterIds());
    }




    /**
     * 通过用户id获取用户简单VO信息 (账号 / 是否管理员)
     */
    @GetMapping("/user_simple")
    Result<UserVO> getUserDOInfo(@RequestParam Long creatorUserId) {
        return Result.success(usersFlow.getUserDOInfo(creatorUserId));
    }
    //http://localhost:10000/Guest/users/user_simple?creatorUserId=1

    /**
     * 通过用户ids获取用户简单VO信息 (账号 / 是否管理员) 批量查询
     */
    @PostMapping("/user_simple/batch")
    Result<List<UserVO>> getUserDOInfoBatch(
            @RequestBody List<Long> creatorUserIds) {
        return Result.success(usersFlow.getUserDOInfoBatch(creatorUserIds));
    }


    /**
     * 当前用户加入群组
     */
    @PostMapping("/cluster/join")
    Result<Object> joinCluster(@RequestParam Long clusterId) {
        usersFlow.joinCluster(clusterId);
        return Result.success();
    }
    //http://localhost:10000/Guest/users/cluster/join?clusterId=1


    /**
     * 群主加入群组
     */
    @PostMapping("/cluster/creator_join")
    void creatorJoinCluster(@RequestParam Long clusterId) {
        usersFlow.creatorJoinCluster(clusterId);
    }
    //http://localhost:10000/Guest/users/cluster/creator_join?id=1


    /**
     * 当前用户退出群组
     */
    @DeleteMapping("/cluster/quit")
    Result<Object> quitCluster(@RequestParam Long clusterId) {
        usersFlow.quitCluster(clusterId);
        return Result.success();
    }
    //http://localhost:10000/Guest/users/cluster/quit?clusterId=1


    /**
     * 踢出某群的某人
     */
    @DeleteMapping("/cluster/kick_out")
    Result<Object> kickOutPopOfCluster(@RequestParam Long clusterId, @RequestParam Long userId) {
        usersFlow.kickOutPopOfCluster(clusterId, userId);
        return Result.success();
    }
    //http://localhost:10000/Guest/users/cluster/kick_out?clusterId=1&userId=2


    /**
     * 所有人退出某群组
     */
    @DeleteMapping("/cluster/every_quit")
    Result<Object> everyQuitCluster(@RequestParam Long clusterId) {
        usersFlow.everyQuitCluster(clusterId);
        return Result.success();
    }
    //http://localhost:10000/Guest/users/cluster/every_quit?clusterId=1


    /**
     * 计算中间表获取对应群组中用户数量
     */
    @GetMapping("/cluster/user/count")
    Result<Integer> getClusterUserCount(@RequestParam Long clusterId) {
        return Result.success(usersFlow.getClusterUserCount(clusterId));
    }


    //! Func

    /**
     * 获取手机验证码
     */
    @GetMapping("/code")
    @Operation(summary = "验证码")
    @Parameters(@Parameter(name = "phone", description = "手机号", required = true))
    @SentinelResource(value = SentinelPath.GET_LOGIN_CODE_PATH, blockHandler = "getLoginCodeBlockHandlerMethod", blockHandlerClass = CustomBlockHandler.class)
    public Result<String> getCode(
            @RequestParam("phone")
            @Xss(message = "手机号不能包含脚本字符")
            @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
            @NotEmpty(message = "登陆手机号不能为空")
            String phone
    ) {
        String code = usersFlow.sendCode(phone);

        if (code.startsWith("!")) {//如果是!开头的字符串，说明发送失败
            return Result.fail(code.substring(1));
        }
        return Result.success(code);
    }
    //http://localhost:10000/Guest/users/code?phone=15985785169


    /**
     * 登录
     */
    @PostMapping("/login")
    @Operation(summary = "登录")
    @Parameters(@Parameter(name = "userLoginDTO", description = "用户登录DTO", required = true))
    public Result<String> login(
            @RequestBody
            @Validated({UsersValiClusters.Common.class, UsersValiClusters.Login.class}) //登陆校验组, 减少Service层校验
            UserDTO userDTO
    ) {
        String token = usersFlow.login(userDTO);
        return StrUtil.isBlank(token) ? Result.fail("登录失败") : Result.success(token);
    }
    //http://localhost:10000/Guest/users/login

    /**
     * 登出
     */
    @DeleteMapping("/logout")
    @Operation(summary = "登出")
    @Parameters(@Parameter(name = "无", description = "无", required = true))
    public Result<String> logout() {
        log.debug(UserContext.getUA() + "已登出");
        return usersFlow.logout() ? Result.success("用户已登出") : Result.fail("用户登出失败");
    }
    //http://localhost:10000/Guest/users/logout

    /**
     * 注册
     */
    @RateLimiter(value = 1, timeout = 5, limitType = LimitTypeEnum.IP, timeUnit = TimeUnit.SECONDS) //限流 = 1 QPS; 超时 = 2 min; Base on IP
    @PostMapping("/register")
    @Operation(summary = "注册")
    @Parameters(@Parameter(name = "userLoginDTO", description = "用户登录DTO", required = true))
    public Result<String> register(
            @RequestBody
            @Validated({UsersValiClusters.Common.class, UsersValiClusters.Register.class})
            UserDTO userDTO
    ) {
        return usersFlow.register(userDTO) ? Result.success("注册成功") : Result.fail("注册失败");
    }
    //http://localhost:10000/Guest/users/register

    //! ADD

    //! DELETE

    /**
     * 逻辑删除账号: 直接修改状态为-已注销
     */
    @DeleteMapping("/killme")
    @Operation(summary = "注销账号")
    @Parameters(@Parameter(name = "id", description = "用户id", required = true))
    public Result<Object> killUserAccount(@RequestParam Long id) {
        usersFlow.killUserAccount(id);
        return Result.success();
    }
    //http://localhost:10000/Guest/users/killme

    //! UPDATE

    /**
     * 更新用户信息
     */
    @PutMapping("/user_info")
    @Operation(summary = "更新用户信息")
    @Parameters(@Parameter(name = "userGreatVO", description = "用户完整信息", required = true))
    public Result<Object> updateUserInfo(@RequestBody UserGreatVO userGreatVO) {
        usersFlow.updateUserInfo(userGreatVO);
        return Result.success();
    }
    //http://localhost:10000/Guest/users/user_info

    //! Query


    /**
     * 用户信息全部查询, 联表三张 + 加入的群组Name.
     * <热点>
     */
    @GetMapping("/user_info")
    @Operation(summary = "查用户信息")
    @Parameter(name = "id", description = "用户id", required = true)
    public Result<UserGreatVO> getUserInfo(@NotNull @RequestParam("id") Long id) {
        return Result.success(usersFlow.getUserInfoWithClusters(id));
    }
    //http://localhost:10000/Guest/users/user_info


    /**
     * 获取某个群组中所有用户的清单
     */
    @GetMapping("/cluster/user_list")
    @Operation(summary = "获取某个群组中所有用户的清单")
    @Parameters(@Parameter(name = "clusterId", description = "群组id", required = true))
    public Result<List<UserVO>> getClusterUserList(
            @NotNull(message = "群组id不能为空")
            @RequestParam("clusterId") Long clusterId
    ) {
        return Result.success(usersFlow.getClusterUserList(clusterId));
    }
    //http://localhost:10000/Guest/users/cluster/user_list?clusterId=1


    /**
     * 查用户加入的群组id清单 - 只用于展示
     */
    @GetMapping("/user_clusters/show")
    public Result<List<String>> getUserClusterIdsShow() {
        List<Long> userClusterIds = usersFlow.getUserClusterIds();
        List<String> res = userClusterIds.stream()
                .map(String::valueOf)
                .toList();

        return Result.success(res);
    }
}
