package xyz.spc.infra.special.Guide.feedbacks;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guide.feedbacks.AdminNoticeMapper;
import xyz.spc.infra.repo.Guide.feedbacks.AdminNoticeService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class AdminNoticesRepo {

    public final AdminNoticeService adminNoticeService;
    public final AdminNoticeMapper adminNoticeMapper;
}
