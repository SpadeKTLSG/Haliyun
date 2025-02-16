package xyz.spc.infra.special.Guide.logs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guide.logs.FileOpLogMapper;
import xyz.spc.infra.mapper.Guide.logs.LoginLogMapper;
import xyz.spc.infra.mapper.Guide.logs.ReqLogMapper;
import xyz.spc.infra.repo.Guide.logs.FileOpLogService;
import xyz.spc.infra.repo.Guide.logs.LoginLogService;
import xyz.spc.infra.repo.Guide.logs.ReqLogService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class LogsRepo {

    public final LoginLogService loginLogService;
    public final LoginLogMapper loginLogMapper;
    public final ReqLogService reqLogService;
    public final ReqLogMapper reqLogMapper;
    public final FileOpLogService fileOpLogService;
    public final FileOpLogMapper fileOpLogMapper;


}

