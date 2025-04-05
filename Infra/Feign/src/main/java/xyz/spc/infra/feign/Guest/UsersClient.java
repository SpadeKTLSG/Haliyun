package xyz.spc.infra.feign.Guest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Guest.users.UserVO;

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
    @GetMapping(BASE_URL + "/user_clusters")
    Result<List<Long>> getUserClusterIds();

    /**
     * 通过用户id获取用户简单VO信息 (账号 / 是否管理员)
     */
    @GetMapping(BASE_URL + "/user_simple")
    Result<UserVO> getUserDOInfo(@RequestParam Long creatorUserId);


    /**
     * 当前用户的加入群组
     */
    @PostMapping(BASE_URL + "/cluster/join")
    Result<Object> joinCluster(@RequestParam Long clusterId);

    /**
     * 群主的加入群组
     */
    @PostMapping(BASE_URL + "/cluster/creator_join")
    void creatorJoinCluster(@RequestParam Long clusterId);

    /**
     * 当前用户的退出群组
     */
    @DeleteMapping(BASE_URL + "/cluster/quit")
    Result<Object> quitCluster(@RequestParam Long clusterId);

    /**
     * 所有人的退出群组
     */
    @DeleteMapping(BASE_URL + "/cluster/every_quit")
    void everyQuitCluster(@RequestParam Long clusterId);

}
