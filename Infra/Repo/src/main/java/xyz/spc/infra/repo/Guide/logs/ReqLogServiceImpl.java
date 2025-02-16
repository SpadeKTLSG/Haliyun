package xyz.spc.infra.repo.Guide.logs;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.logs.ReqLogDO;
import xyz.spc.infra.mapper.Guide.logs.ReqLogMapper;

@Service
@RequiredArgsConstructor
public class ReqLogServiceImpl extends ServiceImpl<ReqLogMapper, ReqLogDO> implements ReqLogService {
}
