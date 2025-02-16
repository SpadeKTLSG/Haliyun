package xyz.spc.infra.repo.Guide.risks;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.risks.RisklogDO;
import xyz.spc.infra.mapper.Guide.risks.RisklogMapper;

@Service
@RequiredArgsConstructor
public class RisklogServiceImpl extends ServiceImpl<RisklogMapper, RisklogDO> implements RisklogService {
}
