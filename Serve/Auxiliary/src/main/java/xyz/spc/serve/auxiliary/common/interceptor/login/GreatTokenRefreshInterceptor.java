package xyz.spc.serve.auxiliary.common.interceptor.login;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.spc.common.constant.Guest.users.LoginCacheKey;
import xyz.spc.common.constant.Guest.users.LoginCommonCT;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.auxiliary.config.redis.tool.RedisCacheGeneral;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Token刷新拦截器 (首次从网关过来的请求, 处理Token刷新)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GreatTokenRefreshInterceptor implements HandlerInterceptor {

    private final RedisCacheGeneral rcg;


    @Override
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {

        String userInfo = request.getHeader("user-all-info"); //尝试获取OpenFeign存储的用户信息
        String token = request.getHeader("authorization");
        if (!StrUtil.isBlank(userInfo) && !StrUtil.isBlank(token)) { //如果已经是通过OpenFeign调用, 无需刷新Token操作, 直接返回
            return true;
        }

        // 获取网关设置的UserContext JSON对象
        String saved_info = request.getHeader("userDto");
        if (StrUtil.isBlank(saved_info) || StrUtil.isBlank(token)) {
            throw new ServiceException(ServerError.SERVICE_ERROR);
        }

        if (token.startsWith("Bearer")) { //去除Postman产生的Bearer前缀
            token = token.substring(7);
        }

        UserDTO user = Optional.ofNullable(JSONUtil.toBean(saved_info, UserDTO.class)).orElseThrow(() -> new ServiceException("用户存储信息转换失败", ServerError.SERVICE_ERROR));

        //刷新token有效期
        rcg.expire(LoginCacheKey.LOGIN_USER_KEY + user.getAccount(), LoginCommonCT.LOGIN_USER_TTL, TimeUnit.MINUTES);
        UserContext.setUser(user);

        log.debug("登陆Haliyun用户JSON: " + saved_info);
        return true;
    }


    /**
     * 请求结束后移除管理员/用户信息
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        UserContext.removeUser();
    }
}
