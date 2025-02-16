package xyz.spc.infra.mapper.Data.files;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Data.files.FileDO;

@Mapper
public interface FileMapper extends MPJBaseMapper<FileDO> {
}
