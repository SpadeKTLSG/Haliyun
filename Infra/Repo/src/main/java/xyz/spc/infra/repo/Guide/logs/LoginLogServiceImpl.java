package xyz.spc.infra.repo.Guide.logs;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.logs.LoginLogDO;
import xyz.spc.infra.mapper.Guide.logs.LoginLogMapper;

@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogDO> implements LoginLogService {
}
