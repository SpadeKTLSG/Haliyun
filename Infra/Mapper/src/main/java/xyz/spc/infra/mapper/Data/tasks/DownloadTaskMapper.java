package xyz.spc.infra.mapper.Data.tasks;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Data.tasks.DownloadTaskDO;

@Mapper
public interface DownloadTaskMapper extends MPJBaseMapper<DownloadTaskDO> {
}
