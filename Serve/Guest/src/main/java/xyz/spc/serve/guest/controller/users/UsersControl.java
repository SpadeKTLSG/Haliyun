package xyz.spc.serve.guest.controller.users;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.constant.sentinel.SentinelPath;
import xyz.spc.common.funcpack.commu.Result;
import xyz.spc.common.funcpack.validate.Guest.UsersValiGroups;
import xyz.spc.common.funcpack.xss.Xss;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.infra.feign.Guest.users.UsersClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.auxiliary.config.senti.CustomBlockHandler;
import xyz.spc.serve.guest.func.users.UsersFunc;

import javax.security.auth.login.AccountNotFoundException;

@Slf4j
@Tag(name = "Users", description = "用户合集")
@RequestMapping("/Guest/users")
@RestController
@RequiredArgsConstructor
public class UsersControl {

    private final UsersFunc usersFunc;
    private final UsersClient usersClient;

    //! Client

    //! Func

    /**
     * 获取手机验证码
     */
    @GetMapping("code")
    @Operation(summary = "验证码")
    @Parameters(@Parameter(name = "phone", description = "手机号", required = true))
    @SentinelResource(value = SentinelPath.GET_LOGIN_CODE_PATH, blockHandler = "getLoginCodeBlockHandlerMethod", blockHandlerClass = CustomBlockHandler.class)
    public Result<String> getCode(
            @RequestParam("phone")
            @Xss(message = "手机号不能包含脚本字符")
            @NotNull(message = "手机号不能为空")
            String phone
    ) {

        String code = usersFunc.sendCode(phone);

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
            @Validated({UsersValiGroups.Login.class}) //登陆校验组
            UserDTO userDTO
    ) throws AccountNotFoundException {

        String token = usersFunc.login(userDTO);

        if (StrUtil.isBlank(token)) {
            return Result.fail("登录失败");
        }
        return Result.success(token);
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
        return usersFunc.logout() ? Result.success("用户已登出") : Result.fail("用户登出失败");
    }
    //http://localhost:10000/Guest/users/logout

    /**
     * 注册
     */
    //todo


    //! ADD

    //! DELETE

    //! UPDATE

    //! Query

}
