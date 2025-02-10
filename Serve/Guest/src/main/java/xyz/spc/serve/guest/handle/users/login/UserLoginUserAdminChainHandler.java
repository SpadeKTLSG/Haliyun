package xyz.spc.serve.guest.handle.users.login;

import org.springframework.stereotype.Component;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;

import java.util.Objects;

/**
 * 用户登录校验用户类型责任链处理器
 */
@Component
public class UserLoginUserAdminChainHandler implements UserLoginChainFilter<User, UserDTO> {

    @Override
    public void handler(User user, UserDTO userDTO) {
        if (!Objects.equals(user.getAdmin(), userDTO.getAdmin())) {
            throw new ClientException(ClientError.USER_ADMIN_TYPE_NOT_EQUAL);
        }
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
