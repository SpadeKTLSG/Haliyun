package xyz.spc.infra.repo.Guide.feedbacks;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.feedbacks.FeedbackDO;
import xyz.spc.infra.mapper.Guide.feedbacks.FeedbackMapper;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, FeedbackDO> implements FeedbackService {
}
