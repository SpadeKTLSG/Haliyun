package xyz.spc.infra.feign.Guest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.Result;

import java.util.List;
import java.util.Map;

@FeignClient(name = "guest-app")
public interface UsersClient {

    String BASE_URL = "/Guest/users";

    /**
     * 获取手机验证码
     */
    @GetMapping(BASE_URL + "/code")
    Result<String> getLoginCode(@RequestParam("phone") String phone);

    /**
     * 远程调用用户信息Map
     */
    @GetMapping(BASE_URL + "user_map")
    Map<Object, Object> getUserMap(String tokenKey);


    /**
     * 获取用户加入的群组id清单
     */
    @GetMapping("/user_clusters")
    Result<List<Long>> getUserClusterIds();
}
