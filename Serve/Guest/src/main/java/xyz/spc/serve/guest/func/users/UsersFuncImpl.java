package xyz.spc.serve.guest.func.users;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.constant.Guest.LoginCacheKey;
import xyz.spc.common.constant.Guest.LoginCommonCT;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.AbstractException;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.uuid.UUID;
import xyz.spc.common.util.encryptUtil.MD5Util;
import xyz.spc.common.util.userUtil.PhoneUtil;
import xyz.spc.common.util.userUtil.codeUtil;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.infra.special.Guest.users.UsersRepo;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.auxiliary.config.design.chain.AbstractChainContext;
import xyz.spc.serve.auxiliary.config.redis.tool.RedisCacheGeneral;
import xyz.spc.serve.guest.common.enums.UsersChainMarkEnum;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsersFuncImpl implements UsersFunc {

    /**
     * Repo
     */
    private final UsersRepo usersRepo;

    /**
     * 责任链
     */
    private final AbstractChainContext<User, UserDTO> abstractChainContext;


    /**
     * 中间件
     */
    private final RedisTemplate<Object, Object> redisTemplate;
    private final RedisCacheGeneral rcg;
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Override
    @Retryable(retryFor = ServiceException.class, backoff = @Backoff(delay = 1000, multiplier = 1.5)) //重试策略, 通常在依赖外部服务时使用
    public String sendCode(String phone) {

        //? 限流策略
        // 1. 判断是否在一级限制条件内
        Boolean oneLevelLimit = rcg.hasKey(LoginCacheKey.ONE_LEVERLIMIT_KEY + phone);
        if (oneLevelLimit != null && oneLevelLimit) {
            return "!您需要等5分钟后再请求";
        }

        // 2. 判断是否在二级限制条件内
        Boolean twoLevelLimit = rcg.hasKey(LoginCacheKey.TWO_LEVERLIMIT_KEY + phone);
        if (twoLevelLimit != null && twoLevelLimit) {
            return "!您需要等20分钟后再请求";
        }

        // 3. 检查过去1分钟内发送验证码的次数
        long oneMinuteAgo = System.currentTimeMillis() - 60 * 1000;
        long count_oneminute = redisTemplate.opsForZSet().count(LoginCacheKey.SENDCODE_SENDTIME_KEY + phone, oneMinuteAgo, System.currentTimeMillis());
        if (count_oneminute >= 1) {
            return "!距离上次发送时间不足1分钟, 请1分钟后重试";
        }

        // 4. 检查发送验证码的次数
        long fiveMinutesAgo = System.currentTimeMillis() - 5 * 60 * 1000;
        long count_fiveminute = redisTemplate.opsForZSet().count(LoginCacheKey.SENDCODE_SENDTIME_KEY + phone, fiveMinutesAgo, System.currentTimeMillis());

        if (count_fiveminute == 5) {
            redisTemplate.opsForSet().add(LoginCacheKey.ONE_LEVERLIMIT_KEY + phone, "1");
            redisTemplate.expire(LoginCacheKey.ONE_LEVERLIMIT_KEY + phone, 5, TimeUnit.MINUTES);
            return "!5分钟内您已经发送了5次, 请等待5分钟后重试";  // 过去5分钟内已经发送了5次，进入一级限制
        }
        if (count_fiveminute > 5) {
            redisTemplate.opsForSet().add(LoginCacheKey.TWO_LEVERLIMIT_KEY + phone, "1");
            redisTemplate.expire(LoginCacheKey.TWO_LEVERLIMIT_KEY + phone, 20, TimeUnit.MINUTES);
            return "!请求过于频繁, 请20分钟后再请求"; // 进入二级限制

        }

        //? 校验策略

        //校验手机号
        if (!PhoneUtil.isMatches(phone, true)) {
            throw new ClientException(ClientError.PHONE_VERIFY_ERROR);
        }

        //? 生成策略

        //删除之前的验证码
        Set<String> keys = (Set<String>) rcg.keys(LoginCacheKey.LOGIN_CODE_KEY + phone + "*");
        if (keys != null) {
            rcg.deleteObject(keys);
        }

        //生成验证码
        String code = codeUtil.achieveCode(); //自定义工具类生成验证码
        rcg.setCacheObject(LoginCacheKey.LOGIN_CODE_KEY + phone, code, Math.toIntExact(LoginCacheKey.LOGIN_CODE_TTL_GUEST), TimeUnit.MINUTES);
        // 更新发送时间和次数
        redisTemplate.opsForZSet().add(LoginCacheKey.SENDCODE_SENDTIME_KEY + phone, System.currentTimeMillis() + "", System.currentTimeMillis());

        return code; //调试环境: 返回验证码; 可选择使用邮箱工具类发送验证码
    }


    @Override
    @Transactional(rollbackFor = AbstractException.class, timeout = 30)
    public String login(UserDTO userDTO) throws AccountNotFoundException {


        Integer login_type = Optional.ofNullable(userDTO.getLoginType()).orElseThrow(() -> new ClientException("登陆方式不能为空", ClientError.USER_REGISTER_ERROR));

        //登陆受理(控制并发)
        //如果requestNo不存在则返回1,如果已经存在,则会返回（requestNo已存在个数+1）
        String requestNOKey = LoginCacheKey.LOGIN_REQUEST_ONLY_KEY + userDTO.getAccount();
        Long count = redisTemplate.opsForValue().increment(requestNOKey, 1);

        if (count != null && count == 1) { //第一次请求, 放行并设置5s操作窗口
            redisTemplate.expire(requestNOKey, 5, TimeUnit.SECONDS); //  5s内只能请求一次登陆
        } else { //已有线程在执行该操作，直接返回“正在处理”
            throw new ClientException("系统正在处理", ClientError.USER_LOGIN_ERROR);
        }


        // 确定登陆方式: 手动策略模式
        return switch (login_type) {
            case User.LOGIN_TYPE_ACCOUNT -> loginByAccount(userDTO);
            case User.LOGIN_TYPE_PHONE -> loginByPhone(userDTO);
            case User.LOGIN_TYPE_EMAIL -> loginByEmail(userDTO);
            case User.LOGIN_TYPE_ACCOUNT_PHONE -> loginByAccountPhone(userDTO);
            default -> throw new ClientException("登陆方式不正确", ClientError.USER_REGISTER_ERROR);
        };
    }

    @Override
    public Boolean logout() {
        //清除登陆Token
        String tokenKey = LoginCacheKey.LOGIN_USER_KEY + UserContext.getUser().getAccount();
        return rcg.deleteObject(tokenKey);
    }

    private String loginByAccountPhone(UserDTO userDTO) {
        //? 目前暂时只选择此方式 note: 根据用户名查询用户 | 根据手机号查询用户, 这里后者

        //! DB取Model
        User user = usersRepo.getUserByUserDTO(userDTO, UserDTO.UserDTOField.phone);

        //! 落库校验 (责任链)
        abstractChainContext.handler(UsersChainMarkEnum.USER_LOGIN_FILTER.name(), user, userDTO);

        //! 登陆业务
        //1 Account作为MD5 salt生成token
        String token = MD5Util.enryption(UUID.randomUUID(false).toString(), user.getAccount());

        //2 制作用户信息Map (去除密码code加入Token)
        userDTO.setPassword(null).setCode(null).setToken(token);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true).setFieldValueEditor((fieldName, fieldValue) -> fieldValue == null ? null : fieldValue.toString()));

        //3 存储用户信息到redis
        String tokenKey = LoginCacheKey.LOGIN_USER_KEY + userDTO.getAccount();
        rcg.deleteObject(tokenKey);
        rcg.setCacheMap(tokenKey, userMap);
        rcg.expire(tokenKey, LoginCommonCT.LOGIN_USER_TTL, TimeUnit.MINUTES);

        return token;
    }

    private String loginByEmail(UserDTO userDTO) {
        throw new ClientException("暂不支持邮箱登陆, 敬请期待", ClientError.USER_LOGIN_ERROR);
    }

    private String loginByPhone(UserDTO userDTO) {
        if (userDTO.getAdmin() == 0) {
            throw new ClientException("安全原因, 用户暂不支持仅手机验证码登陆", ClientError.USER_LOGIN_ERROR);
        }
        throw new ClientException("安全原因, 管理员暂不支持仅手机验证码登陆", ClientError.USER_LOGIN_ERROR);
    }

    private String loginByAccount(UserDTO userDTO) {
        if (userDTO.getAdmin() == 0) {
            throw new ClientException("安全原因, 用户暂不支持仅账号密码登陆", ClientError.USER_LOGIN_ERROR);
        }
        throw new ClientException("安全原因, 管理员暂不支持仅账号密码登陆", ClientError.USER_LOGIN_ERROR);
    }


    //  加锁注册流程
//        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
//        if (!lock.tryLock()) {
//            throw new ClientException(USER_NAME_EXIST);
//        }
//        try {
//            int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
//            if (inserted < 1) {
//                throw new ClientException(USER_SAVE_ERROR);
//            }
//            groupService.saveGroup(requestParam.getUsername(), "默认分组");
//            userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
//        } catch (DuplicateKeyException ex) {
//            throw new ClientException(USER_EXIST);
//        } finally {
//            lock.unlock();
//        }


    //register
    //    public Boolean hasUsername(String username) {
    //        return !userRegisterCachePenetrationBloomFilter.contains(username);
    //    }
    //userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());

}
