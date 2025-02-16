package xyz.spc.infra.repo.Guide.feedbacks;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.feedbacks.AdminNoticeDO;
import xyz.spc.infra.mapper.Guide.feedbacks.AdminNoticeMapper;

@Service
@RequiredArgsConstructor
public class AdminNoticeServiceImpl extends ServiceImpl<AdminNoticeMapper, AdminNoticeDO> implements AdminNoticeService {
}
