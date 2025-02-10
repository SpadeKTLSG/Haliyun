package xyz.spc.serve.auxiliary.config.redis.compo;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import xyz.spc.common.constant.Guest.users.LoginCacheKey;


/**
 * 监听redisKey过期回调
 */
@Component
@Slf4j
public class RedisKeyExpirationListerner extends KeyExpirationEventMessageListener {


    public RedisKeyExpirationListerner(@NotNull RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
        log.debug("Redis消息监听器初始化完成");
    }

    /**
     * 监听消息处理
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String keyExpire = message.toString();
        String id = keyExpire.substring(keyExpire.indexOf(':') + 1);

        // 对于指定前缀的key进行处理:
        if (keyExpire.startsWith(LoginCacheKey.LOGIN_USER_KEY)) {
            log.debug("key为：" + keyExpire + "的用户登录令牌已经过期, id: " + id);
        }

    }

}
