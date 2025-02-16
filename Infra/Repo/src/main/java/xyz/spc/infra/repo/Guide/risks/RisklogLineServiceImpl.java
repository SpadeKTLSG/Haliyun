package xyz.spc.infra.repo.Guide.risks;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.risks.RisklogLineDO;
import xyz.spc.infra.mapper.Guide.risks.RisklogLineMapper;

@Service
@RequiredArgsConstructor
public class RisklogLineServiceImpl extends ServiceImpl<RisklogLineMapper, RisklogLineDO> implements RisklogLineService {
}
