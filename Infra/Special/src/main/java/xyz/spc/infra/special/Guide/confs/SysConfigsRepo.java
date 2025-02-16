package xyz.spc.infra.special.Guide.confs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guide.confs.SysConfigMapper;
import xyz.spc.infra.repo.Guide.confs.SysConfigService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class SysConfigsRepo {

    public final SysConfigService sysConfigService;
    public final SysConfigMapper sysConfigMapper;

}
