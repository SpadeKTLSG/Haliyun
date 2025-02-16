package xyz.spc.infra.mapper.Guest.messages;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Guest.messages.SelfMailDO;

@Mapper
public interface SelfMailMapper extends MPJBaseMapper<SelfMailDO> {
}
