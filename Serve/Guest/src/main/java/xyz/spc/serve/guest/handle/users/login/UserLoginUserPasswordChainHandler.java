package xyz.spc.serve.guest.handle.users.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.spc.common.constant.Guest.users.LoginCacheKey;
import xyz.spc.common.constant.Guest.users.LoginCommonCT;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.serve.auxiliary.config.redis.compo.StringRedisTemplateProxy;

import java.util.concurrent.TimeUnit;

/**
 * 用户登录校验用户密码责任链处理器
 */
@Component
@RequiredArgsConstructor
public class UserLoginUserPasswordChainHandler implements UserLoginChainFilter<User, UserDTO> {

    private final StringRedisTemplateProxy redisTemplate;

    @Override
    public void handler(User user, UserDTO userDTO) {

        //看看用户是否被锁定 (简化: 直接看其密码错误次数, 不进行DB的锁定状态修改)
        Long count = redisTemplate.get(LoginCacheKey.PWD_ERR_CNT_KEY + userDTO.getPhone(), Long.class);
        if (count != null && count >= LoginCommonCT.PWD_ERR_CNT_LIMIT) {
            throw new ClientException(ClientError.USER_ACCOUNT_BLOCKED_ERROR);
        }

        if (!user.passwordEquals(userDTO.getPassword())) {
            //密码错误需要记录错误次数
            redisTemplate.put(LoginCacheKey.PWD_ERR_CNT_KEY + userDTO.getPhone(), 1L, LoginCommonCT.PWD_ERR_CNT_FREEZE_TIME, TimeUnit.DAYS);
            throw new ClientException(ClientError.USER_PASSWORD_ERROR);
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
