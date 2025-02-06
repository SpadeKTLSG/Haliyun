package xyz.spc.serve.auxiliary.common.interceptor.login;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.serve.auxiliary.common.context.UserContext;

/**
 * 登录拦截器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GreatLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {

        //通过OpenFeign调用, 请求头中的TL对象需要被取出 (OpenFeign调用时, 一定会在请求头中携带用户信息)
        String userInfo = request.getHeader("user-all-info");
        if (userInfo != null) {
            UserContext.removeUser();
            UserDTO user = JSONUtil.toBean(userInfo, UserDTO.class);
            UserContext.setUser(user);
        }

        return true;
    }
}
