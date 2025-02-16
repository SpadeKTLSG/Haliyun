package xyz.spc.infra.repo.Guide.counts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.counts.PlatformStatusDO;
import xyz.spc.infra.mapper.Guide.counts.PlatformStatusMapper;

@Service
@RequiredArgsConstructor
public class PlatformStatusServiceImpl extends ServiceImpl<PlatformStatusMapper, PlatformStatusDO> implements PlatformStatusService {
}
