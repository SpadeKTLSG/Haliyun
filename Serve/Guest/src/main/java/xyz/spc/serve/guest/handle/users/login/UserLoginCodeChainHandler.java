package xyz.spc.serve.guest.handle.users.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.spc.common.constant.Guest.LoginCacheKey;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.util.collecUtil.StringUtil;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.serve.auxiliary.config.redis.tool.RedisCacheGeneral;

/**
 * 用户登录校验验证码责任链处理器
 */
@Component
@RequiredArgsConstructor
public class UserLoginCodeChainHandler implements UserLoginChainFilter<User, UserDTO> {

    private final RedisCacheGeneral rcg;

    @Override
    public void handler(User user, UserDTO userDTO) {
        String cacheCode = rcg.getCacheObject(LoginCacheKey.LOGIN_CODE_KEY + userDTO.getPhone());
        String code = userDTO.getCode();
        if (StringUtil.isBlank(cacheCode) || !cacheCode.equals(code)) {
            throw new ClientException(ClientError.USER_CODE_ERROR);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
