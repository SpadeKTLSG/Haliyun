package xyz.spc.serve.guest.func.users;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.constant.LoginCommonCT;
import xyz.spc.common.constant.redis.LoginCacheKey;
import xyz.spc.common.funcpack.commu.errorcode.ClientError;
import xyz.spc.common.funcpack.commu.exception.ClientException;
import xyz.spc.common.funcpack.commu.exception.ServiceException;
import xyz.spc.common.funcpack.uuid.UUID;
import xyz.spc.common.util.collecUtil.StringUtil;
import xyz.spc.common.util.encryptUtil.MD5Util;
import xyz.spc.common.util.userUtil.PhoneUtil;
import xyz.spc.common.util.userUtil.codeUtil;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.dos.Guest.users.UserDetailDO;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.infra.special.Guest.users.UsersRepo;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.auxiliary.config.redis.RedisCacheGeneral;

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

    private final RedisTemplate<Object, Object> redisTemplate;
    private final RedisCacheGeneral rcg;
    private final UsersRepo usersRepo;


    @Override
    @Retryable(retryFor = ServiceException.class, backoff = @Backoff(delay = 1000, multiplier = 1.5)) //重试策略, 通常在依赖外部服务时使用
    public String sendCode(String phone, HttpSession session) {

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
    public String login(UserDTO userDTO, HttpSession session) throws AccountNotFoundException {

        Integer login_type = Optional.ofNullable(userDTO.getLoginType()).orElseThrow(
                () -> new ClientException("登陆方式不能为空", ClientError.USER_REGISTER_ERROR)
        );

        // 确定登陆方式
        return switch (login_type) {
            case User.LOGIN_TYPE_ACCOUNT -> loginByAccount(userDTO, session);
            case User.LOGIN_TYPE_PHONE -> loginByPhone(userDTO, session);
            case User.LOGIN_TYPE_EMAIL -> loginByEmail(userDTO, session);
            case User.LOGIN_TYPE_ACCOUNT_PHONE -> loginByAccountPhone(userDTO, session);
            default -> throw new ClientException("登陆方式不正确", ClientError.USER_REGISTER_ERROR);
        };
    }

    @Override
    public Boolean logout() {
        //清除登陆Token
        String tokenKey = LoginCacheKey.LOGIN_USER_KEY + UserContext.getUser().getAccount();
        return rcg.deleteObject(tokenKey);
    }

    private String loginByAccountPhone(UserDTO userDTO, HttpSession session) throws AccountNotFoundException {
        //? 目前暂时只选择此方式
        //note: 根据用户名查询用户 | 根据手机号查询用户, 这里后者

        //! 校验 todo 责任链模式

        //? 1 校验手机号格式
        String phone = userDTO.getPhone();
        if (!PhoneUtil.isMatches(phone, true)) {
            throw new ClientException(ClientError.PHONE_VERIFY_ERROR);
        }


        //查找手机号关联用户 : 去找手机号对应的用户详情DO todo : 抽取到DAO(Service)区域
        LambdaQueryWrapper<UserDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDetailDO::getPhone, phone);
        UserDetailDO userDetailDO = Optional.ofNullable(usersRepo.userDetailService.getOne(queryWrapper)).orElseThrow(
                () -> new AccountNotFoundException("手机号未注册")
        );

        //利用UserDetailDO的id去查找UserDO
        LambdaQueryWrapper<UserDO> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(UserDO::getId, userDetailDO.getId());
        UserDO userDO = usersRepo.userService.getOne(userQueryWrapper);


        //? 2 校验用户是否被锁定了
        User user = new User().fromDO(userDO);
        if (!user.isNormal()) {
            throw new ClientException(ClientError.USER_ACCOUNT_BLOCKED_ERROR);
        }

        //? 3 密码校验
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new ClientException(ClientError.USER_PASSWORD_ERROR);
        }

        //? 4 从redis获取验证码并校验
        String cacheCode = rcg.getCacheObject(LoginCacheKey.LOGIN_CODE_KEY + phone);
        String code = userDTO.getCode();
        if (StringUtil.isBlank(cacheCode) || !cacheCode.equals(code)) throw new ClientException(ClientError.USER_CODE_ERROR);


        //! 登陆
        // 使用用户Account作为MD5 salt生成token
        String key = user.getAccount();
        String text = UUID.randomUUID(false).toString();
        String token = MD5Util.enryption(text, key);

        // 制作用户信息Map (去除密码code加入Token)
        userDTO.setPassword(null);
        userDTO.setCode(null);
        userDTO.setToken(token);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue == null ? null : fieldValue.toString())
        );

        // 存储用户信息到redis
        String tokenKey = LoginCacheKey.LOGIN_USER_KEY + userDTO.getAccount();


        rcg.deleteObject(tokenKey);
        rcg.setCacheMap(tokenKey, userMap);
        rcg.expire(tokenKey, LoginCommonCT.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return token;
    }

    private String loginByEmail(UserDTO userDTO, HttpSession session) {
        throw new ClientException("暂不支持邮箱登陆", ClientError.USER_LOGIN_ERROR);
    }

    private String loginByPhone(UserDTO userDTO, HttpSession session) {
        throw new ClientException("暂不支持手机验证码登陆", ClientError.USER_LOGIN_ERROR);
    }

    private String loginByAccount(UserDTO userDTO, HttpSession session) {
        throw new ClientException("暂不支持账号密码登陆", ClientError.USER_LOGIN_ERROR);
    }

    @Transactional(rollbackFor = Exception.class, timeout = 30)
//    public void register(UserRegisterReqDTO requestParam) {
//        if (!hasUsername(requestParam.getUsername())) {
//            throw new ClientException(USER_NAME_EXIST);
//        }
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
//    }

}
