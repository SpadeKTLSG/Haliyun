package xyz.spc.serve.guest.controller.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.commu.Result;
import xyz.spc.gate.remote.Guest.users.UsersApi;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.serve.guest.func.users.UsersFunc;

@Tag(name = "Users", description = "用户合集")
@RequestMapping("/Guest/users/users")
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
        String mes = usersFunc.sendCode(phone, session);

        if (mes.startsWith("!")) {//如果是!开头的字符串，说明发送失败
            return Result.fail(mes.substring(1));
        }
        return Result.success(mes);
    }
    //http://localhost:10003/Guest/users/users/code?phone=15985785169

    public Result login() {
        return null;
    }


    //! ADD

    //! DELETE
    @Override
    @PostMapping("/add")
    public void add() {

    }

    @Override
    @DeleteMapping("/delete")
    public void delete() {

    }

    @Override
    @PutMapping("/update")
    public void update() {

    }

    @Override
    @GetMapping("/get")
    public Result<UserVO> get() {
        return Result.success(usersFunc.get());
    }
    // http://localhost:10003/Guest/users/users/get
}
