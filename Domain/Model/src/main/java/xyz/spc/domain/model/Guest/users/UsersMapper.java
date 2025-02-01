package xyz.spc.domain.model.Guest.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.model.BaseMapper;

@Mapper
public interface UsersMapper extends BaseMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "updateTime", target = "updateTime")
    @Mapping(source = "delFlag", target = "delFlag")
    UserDO toUserDO(User user);
}
