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

        // 白名单路径
        ServerHttpRequest request = exchange.getRequest();
        if (isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        //note: 网关的过滤不做Redis刷新token操作, 只做token的校验 + TL的传递
        try {
            // 获取请求头中自定义token + (Postman相关处理)
            List<String> headers = Optional.ofNullable(request.getHeaders().get("authorization")).orElseThrow();
            String token = headers.get(0);
            if (StrUtil.isBlank(token)) {
                throw new ClientException("请求头中没有鉴权字段", ClientError.USER_REGISTER_ERROR);
            }

            // 获取请求头中用户 account
            List<String> accounts = Optional.ofNullable(request.getHeaders().get("account")).orElseThrow();
            String account = accounts.get(0);
            if (StrUtil.isBlank(account)) {
                throw new ClientException("请求头中没有用户账号", ClientError.USER_REGISTER_ERROR);
            }

            // 去除Postman产生的Bearer前缀

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }


            //note: 由于前端页面根据用户类型展示不同的页面, 所以这里不需要判断用户类型, 只需要判断用户是否登陆即可

            //基于TOKEN获取redis中的用户DTO Map对象
            String key = LoginCacheKey.LOGIN_USER_KEY + account;

            //获取存储的Map
            Map<Object, Object> userDtoMap = Optional.of(stringRedisTemplate.opsForHash().entries(key)).orElseThrow(() -> new ClientException("网络异常, 请重新登陆"));

            //校验token + account正确性
            if (!token.equals(userDtoMap.get("token")) || !account.equals(userDtoMap.get("account"))) {
                throw new ClientException(ClientError.NOT_LOGIN_ERROR);
            }

            //还原UserDTO打为JSON字符串并传递. 后续的服务可以直接获取到UserDTO对象作为TL

            UserDTO.UserDTOBuilder userDtoBuilder = UserDTO.builder();

            Optional.ofNullable(userDtoMap.get("id"))
                    .map(Object::toString)
                    .map(Long::parseLong)
                    .ifPresent(userDtoBuilder::id);

            Optional.ofNullable(userDtoMap.get("groupId"))
                    .map(Object::toString)
                    .map(Long::parseLong)
                    .ifPresent(userDtoBuilder::groupId);

            Optional.ofNullable(userDtoMap.get("admin"))
                    .map(Object::toString)
                    .map(Integer::parseInt)
                    .ifPresent(userDtoBuilder::admin);

            Optional.ofNullable(userDtoMap.get("status"))
                    .map(Object::toString)
                    .map(Integer::parseInt)
                    .ifPresent(userDtoBuilder::status);

            Optional.ofNullable(userDtoMap.get("loginType"))
                    .map(Object::toString)
                    .map(Integer::parseInt)
                    .ifPresent(userDtoBuilder::loginType);

            Optional.ofNullable(userDtoMap.get("account"))
                    .map(Object::toString)
                    .ifPresent(userDtoBuilder::account);

            Optional.ofNullable(userDtoMap.get("password"))
                    .map(Object::toString)
                    .ifPresent(userDtoBuilder::password);

            Optional.ofNullable(userDtoMap.get("phone"))
                    .map(Object::toString)
                    .ifPresent(userDtoBuilder::phone);

            UserDTO userDto = userDtoBuilder.build();

            String dtoJson = JSONUtil.toJsonStr(userDto);
            ServerWebExchange ex = exchange.mutate().request(a -> a.header("userDto", dtoJson)).build();
            return chain.filter(ex);

        } catch (Exception e) {
            log.error(e.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(HttpStatusCT.UNAUTHORIZED); //401
            return response.setComplete();
        }
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
