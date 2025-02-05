package xyz.spc.serve.guest.handle.users.login;

import org.springframework.stereotype.Component;
import xyz.spc.common.funcpack.commu.errorcode.ClientError;
import xyz.spc.common.funcpack.commu.exception.ClientException;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;

/**
 * 用户登录校验用户状态责任链处理器
 */
@Component
public class UserLoginUserStatusChainHandler implements UserLoginChainFilter<User, UserDTO> {

    @Override
    public void handler(User user, UserDTO userDTO) {
        if (!user.isNormal()) {
            throw new ClientException(ClientError.USER_ACCOUNT_BLOCKED_ERROR);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
