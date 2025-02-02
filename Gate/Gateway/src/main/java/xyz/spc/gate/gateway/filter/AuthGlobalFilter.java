package xyz.spc.gate.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.spc.common.constant.HttpStatusCT;
import xyz.spc.common.constant.redisKey.LoginCacheKey;
import xyz.spc.common.funcpack.commu.errorcode.ClientError;
import xyz.spc.common.funcpack.commu.exception.ClientException;
import xyz.spc.gate.dto.Guest.users.UserDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * 网关全局过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final StringRedisTemplate stringRedisTemplate;
    private final AuthProperties authProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //网关的过滤不做Redis刷新token操作, 只做token的校验 + TL的传递

        ServerHttpRequest request = exchange.getRequest();
        if (isExclude(request.getPath().toString())) { // 判断是否是白名单路径
            return chain.filter(exchange);
        }

        // 获取请求头中自定义token + (Postman相关处理)
        String token;
        List<String> headers = request.getHeaders().get("authorization");
        if (isEmpty(headers)) {
            throw new ClientException("请求头中没有鉴权字段", ClientError.USER_REGISTER_ERROR);
        }
        token = headers.get(0);
        if (StrUtil.isBlank(token)) {
            throw new ClientException("请求头中没有鉴权字段", ClientError.USER_REGISTER_ERROR);
        }

        //去除Postman产生的Bearer前缀
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(HttpStatusCT.UNAUTHORIZED); //401
            return response.setComplete();
        }

        //? 由于前端页面根据用户类型展示不同的页面, 所以这里不需要判断用户类型, 只需要判断用户是否登陆即可

        //基于TOKEN获取redis中的用户Map对象
        String key = LoginCacheKey.LOGIN_USER_ACCOUNT_KEY + token;

        //获取Map
        Map<Object, Object> userDtoMap = Optional.ofNullable(stringRedisTemplate.opsForHash().entries(key)).orElseThrow(() -> new ClientException("网络异常, 请重新登陆"));

        //还原为UserDTO
        UserDTO userDto = UserDTO.builder()
                .id((Long) userDtoMap.get("id"))
                .groupId((Long) userDtoMap.get("groupId"))
                .admin((Integer) userDtoMap.get("admin"))
                .status((Integer) userDtoMap.get("status"))
                .loginType((Integer) userDtoMap.get("loginType"))
                .account((String) userDtoMap.get("account"))
                .password((String) userDtoMap.get("password"))
                .build();

        //打为JSON字符串

        String dtoJson = JSONUtil.toJsonStr(userDto);

        //传递在请求头的自定义DTO (JSON)

        ServerWebExchange ex = exchange.mutate().request(a -> a.header("userDto", dtoJson)).build();
        return chain.filter(ex);
    }

    private boolean isExclude(String antPath) {
        for (String pathPattern : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(pathPattern, antPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
