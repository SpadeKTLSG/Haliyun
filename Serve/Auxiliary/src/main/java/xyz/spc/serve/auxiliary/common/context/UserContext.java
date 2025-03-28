package xyz.spc.serve.auxiliary.common.context;


import com.alibaba.ttl.TransmittableThreadLocal;
import xyz.spc.gate.dto.Guest.users.UserDTO;

import java.util.Optional;

/**
 * 用户上下文
 */
public final class UserContext {

    /**
     * TTL 用于解决线程池中传递 ThreadLocal 的问题
     */
    private static final ThreadLocal<UserDTO> USER_THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 获取上下文中用户 DTO
     */
    public static UserDTO getUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 设置用户至上下文
     */
    public static void setUser(UserDTO user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取上下文中用户 Account
     */
    public static String getUA() {
        UserDTO userDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userDTO).map(UserDTO::getAccount).orElse(null);
    }

    /**
     * 获取上下文中用户 Id
     */
    public static Long getUI() {
        UserDTO userDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userDTO).map(UserDTO::getId).orElse(null);
    }

    /**
     * 清理用户上下文
     */
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}
