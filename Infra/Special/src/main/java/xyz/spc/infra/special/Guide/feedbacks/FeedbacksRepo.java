package xyz.spc.infra.special.Guide.feedbacks;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guide.feedbacks.FeedbackMapper;
import xyz.spc.infra.repo.Guide.feedbacks.FeedbackService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class FeedbacksRepo {

    public final FeedbackService feedbackService;
    public final FeedbackMapper feedbackMapper;
}
