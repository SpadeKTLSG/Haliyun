package xyz.spc.infra.special.Guest.users;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.constant.Guest.users.LoginCommonCT;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.common.funcpack.uuid.IdUtil;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.dos.Guest.users.UserDetailDO;
import xyz.spc.domain.dos.Guest.users.UserFuncDO;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.infra.mapper.Guest.users.UserDetailMapper;
import xyz.spc.infra.mapper.Guest.users.UserFuncMapper;
import xyz.spc.infra.mapper.Guest.users.UserMapper;
import xyz.spc.infra.repo.Guest.users.UserDetailService;
import xyz.spc.infra.repo.Guest.users.UserFuncService;
import xyz.spc.infra.repo.Guest.users.UserService;

/**
 * 用户Repo
 */
@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class UsersRepo {

    public final UserService userService;
    public final UserMapper userMapper;
    public final UserDetailService userDetailService;
    public final UserDetailMapper userDetailMapper;
    public final UserFuncService userFuncService;
    public final UserFuncMapper userFuncMapper;

    public User getUserByUserDTO(UserDTO userDTO, UserDTO.UserDTOField field) throws ClientException {

        UserDO tmp = switch (field) {
            //通过用户DTO的id查找用户DO
            case id -> userService.getById(userDTO.getId());
            //通过用户DTO的account查找用户DO
            case account -> userService.getOne(Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getAccount, userDTO.getAccount()));
            //通过用户DTO的phone查找用户DO, 需要联表查询或者查两次
            case phone ->
                //通过用户详情DO的id查找用户DO:MP版本
                    /*userService.getById(userDetailService.getOne(Wrappers.lambdaQuery(UserDetailDO.class) // 查找手机号对应的用户详情DO
                                    .eq(UserDetailDO::getPhone, userDTO.getPhone()))
                            .getId());*/
                //我只讲一次:
                //MPJ版本 联表查询 note: MySQL升级至8.X最新版后, 推荐采用联表以提升性能
                    userMapper.selectJoinOne(UserDO.class, new MPJLambdaWrapper<UserDO>()
                            .selectAll(UserDO.class)
                            //.select(UserDetailDO::getId, UserDetailDO::getPhone) //下面处理关联和业务的字段可以不查
                            .leftJoin(UserDetailDO.class, UserDetailDO::getId, UserDO::getId) //联表类和对应字段
                            .eq(UserDetailDO::getPhone, userDTO.getPhone()) //业务条件
                    );


            default -> throw new ClientException(ClientError.USER_ACCOUNT_NOT_EXIST_ERROR);
        };
        return new User().fromDO(tmp);
    }


    /**
     * 添加用户
     */
    @Transactional(rollbackFor = Exception.class, timeout = 15)
    public void addUser(UserDTO userDTO) {

        boolean isAdmin = userDTO.getAdmin() != 0;
        // 管理员注册需要额外的校验 使用一张贵宾表来记录信息. 需要时候去拉

        // 注册插入三张表, 生成统一ID
        Long id = SnowflakeIdUtil.nextId();

        //? 插入 UserDO
        UserDO userDO = UserDO.builder()
                .id(id)
                .admin(isAdmin ? 1 : 0)
                .loginType(User.LOGIN_TYPE_ACCOUNT_PHONE)
                .account(userDTO.getAccount())
                .password(userDTO.getPassword()) //note: 前端做Data加密来保证传输过程的安全, 后端于是不做处理落库
                .build();
        userService.save(userDO);
        userService.removeById(userDO); //test

        //? 插入 UserDetailDO
        UserDetailDO userDetailDO = UserDetailDO.builder()
                .id(id)
                .phone(userDTO.getPhone())
                .area(LoginCommonCT.DEFAULT_REGISTER_LOCATION)
                .nickname(IdUtil.fastSimpleUUID().substring(0, 12))
                .introduce(LoginCommonCT.DEFAULT_REGISTER_INTRODUCE)
                .build();
        userDetailService.save(userDetailDO);

        //? 插入 UserFuncDO
        UserFuncDO userFuncDO = UserFuncDO.builder()
                .id(id)
                .registerCode(IdUtil.fastSimpleUUID())
                .build();
        userFuncService.save(userFuncDO);

        log.debug("用户: {} 注册成功: ", userDTO.getAccount()); //todo, 用户注册后在后台拉异步的记录, 记录用户是第几个用户等功能
    }
}
