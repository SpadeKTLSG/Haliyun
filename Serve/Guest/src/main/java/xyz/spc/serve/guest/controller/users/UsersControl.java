package xyz.spc.serve.guest.controller.users;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.commu.Result;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.gate.remote.Guest.users.UsersApi;
import xyz.spc.serve.guest.func.users.UsersFunc;

import javax.security.auth.login.AccountNotFoundException;

@Tag(name = "Users", description = "用户合集")
@RequestMapping("/Guest/users")
@RestController
@RequiredArgsConstructor
public class UsersControl implements UsersApi {

    private final UsersFunc usersFunc;

    //! Client

    //! Func

    /**
     * 获取登陆验证码
     */
    @Override
    @GetMapping("code")
    @Operation(summary = "登陆验证码")
    @Parameters(@Parameter(name = "phone", description = "手机号", required = true))
    public Result<String> getLoginCode(@RequestParam("phone") String phone, HttpSession session) {

        String code = usersFunc.sendCode(phone, session);

        if (code.startsWith("!")) {//如果是!开头的字符串，说明发送失败
            return Result.fail(code.substring(1));
        }
        return Result.success(code);
    }
    //http://localhost:10000/Guest/users/code?phone=15985785169

    @PostMapping("/login")
    @Operation(summary = "登录")
    @Parameters(@Parameter(name = "userLoginDTO", description = "用户登录DTO", required = true))
    public Result<String> loginG(@RequestBody UserDTO userDTO, HttpSession session) throws AccountNotFoundException {

        String token = usersFunc.login(userDTO, session);

        if (StrUtil.isBlank(token)) {
            return Result.fail("登录失败");
        }
        return Result.success(token);
    }


    //! ADD

    //! DELETE

    //! UPDATE

    //! Query

}
