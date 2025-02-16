package xyz.spc.serve.guest.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.infra.feign.Guest.UsersClient;
import xyz.spc.serve.guest.func.records.StatisticFunc;
import xyz.spc.serve.guest.func.records.TombFunc;
import xyz.spc.serve.guest.func.users.UsersFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersFlow {

    //Feign
    private final UsersClient usersClient;

    //Func
    private final UsersFunc usersFunc;
    private final TombFunc tombFunc;
    private final StatisticFunc statisticFunc;


    /**
     * 发送验证码
     */
    public String sendCode(String phone) {
        return usersFunc.sendCodeCore(phone);
    }


    /**
     * 登陆
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public String login(UserDTO userDTO) {
        return usersFunc.loginCore(userDTO); // 后续的登陆操作由前端发送请求再完成, 这里不继续推进后续了
    }

    /**
     * 登出
     */
    public boolean logout() {
        return usersFunc.logoutCore();
    }


    /**
     * 注册
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean register(UserDTO userDTO) {

        //注册用户核心表
        if (!usersFunc.registerCore(userDTO)) {
            return false;
        }
        //注册统计表
        statisticFunc.registerStatistics(userDTO);
        //注册坟墓表
        tombFunc.registerTomb(userDTO);

        return true;
    }


}
