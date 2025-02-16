package xyz.spc.infra.mapper.Guide.feedbacks;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Guide.feedbacks.FeedbackDO;

@Mapper
public interface FeedbackMapper extends MPJBaseMapper<FeedbackDO> {
}
