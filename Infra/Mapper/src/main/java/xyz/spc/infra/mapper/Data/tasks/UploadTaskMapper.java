package xyz.spc.infra.mapper.Data.tasks;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Data.tasks.UploadTaskDO;

@Mapper
public interface UploadTaskMapper extends MPJBaseMapper<UploadTaskDO> {
}
