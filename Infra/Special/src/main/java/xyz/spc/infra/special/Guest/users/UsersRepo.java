package xyz.spc.infra.special.Guest.users;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.commu.errorcode.ClientError;
import xyz.spc.common.funcpack.commu.exception.ClientException;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.dos.Guest.users.UserDetailDO;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.infra.repo.Guest.users.UserDetailService;
import xyz.spc.infra.repo.Guest.users.UserFuncService;
import xyz.spc.infra.repo.Guest.users.UserService;

/**
 * 用户Repo
 */
@Service
@Data
@RequiredArgsConstructor
public class UsersRepo {

    public final UserService userService;
    public final UserDetailService userDetailService;
    public final UserFuncService userFuncService;


    public User getUserByUserDTO(UserDTO userDTO, UserDTO.UserDTOField field) throws ClientException {

        UserDO tmp = switch (field) {
            //通过用户DTO的id查找用户DO
            case id -> userService.getById(userDTO.getId());
            //通过用户DTO的account查找用户DO
            case account -> userService.getOne(Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getAccount, userDTO.getAccount()));
            //通过用户DTO的phone查找用户DO, 需要联表查询或者查两次
            case phone ->
                //MPJ版本 联表查询 note: MySQL升级至8.X最新版后, 推荐采用联表以提升性能
                    userService.getOne(new MPJLambdaWrapper<UserDO>()
                            .leftJoin(UserDetailDO.class, UserDetailDO::getId, UserDO::getId)
                            .eq(UserDetailDO::getPhone, userDTO.getPhone())
                            .selectAll(UserDO.class));

            //通过用户详情DO的id查找用户DO:MP版本
                    /*userService.getById(userDetailService.getOne(Wrappers.lambdaQuery(UserDetailDO.class) // 查找手机号对应的用户详情DO
                                    .eq(UserDetailDO::getPhone, userDTO.getPhone()))
                            .getId());*/

            default -> throw new ClientException(ClientError.USER_ACCOUNT_NOT_EXIST_ERROR);
        };
        return new User().fromDO(tmp);
    }

}
