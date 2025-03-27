package xyz.spc.infra.mapper.Cluster.functions;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Cluster.functions.NoticeDO;

@Mapper
public interface NoticeMapper extends MPJBaseMapper<NoticeDO> {
}
