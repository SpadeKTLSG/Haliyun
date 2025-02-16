package xyz.spc.infra.special.Guide.risks;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guide.risks.RisklogLineMapper;
import xyz.spc.infra.mapper.Guide.risks.RisklogMapper;
import xyz.spc.infra.repo.Guide.risks.RisklogLineService;
import xyz.spc.infra.repo.Guide.risks.RisklogService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class RisksRepo {

    public final RisklogService risklogService;
    public final RisklogMapper risklogMapper;
    public final RisklogLineService risklogLineService;
    public final RisklogLineMapper risklogLineMapper;
}
