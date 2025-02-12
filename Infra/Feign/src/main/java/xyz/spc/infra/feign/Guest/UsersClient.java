package xyz.spc.infra.feign.Guest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.Result;

@FeignClient(name = "guest-app")
public interface UsersClient {

    String BASE_URL = "/Guest/users";

    /**
     * 获取手机验证码
     */
    @GetMapping(BASE_URL + "/code")
    Result<String> getLoginCode(@RequestParam("phone") String phone);
}
