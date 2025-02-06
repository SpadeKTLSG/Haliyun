package xyz.spc.serve.guest.handle.users.login;

import org.springframework.stereotype.Component;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;

/**
 * 用户登录校验用户密码责任链处理器
 */
@Component
public class UserLoginUserPasswordChainHandler implements UserLoginChainFilter<User, UserDTO> {


    @Override
    public void handler(User user, UserDTO userDTO) {
        if (!user.passwordEquals(userDTO.getPassword())) {
            throw new ClientException(ClientError.USER_PASSWORD_ERROR);
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
