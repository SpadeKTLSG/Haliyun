package xyz.spc.gate.remote.Guest.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.commu.Result;
import xyz.spc.gate.vo.Guest.users.UserVO;

public interface UsersApi {


    //test CRUD of Users

    @GetMapping("code")
    @Operation(summary = "登陆验证码")
    @Parameters(@Parameter(name = "phone", description = "手机号", required = true))
    Result<String> getLoginCode(@RequestParam("phone") String phone, HttpSession session);

    void add();


    void delete();


    void update();


    Result<UserVO> get();


}
