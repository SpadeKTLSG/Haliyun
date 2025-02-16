package xyz.spc.infra.special.Guide.counts;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guide.counts.PlatformStatusMapper;
import xyz.spc.infra.repo.Guide.counts.PlatformStatusService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class PlatformStatussRepo {

    public final PlatformStatusService platformStatusService;
    public final PlatformStatusMapper platformStatusMapper;
}
