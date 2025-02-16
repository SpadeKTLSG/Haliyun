package xyz.spc.infra.repo.Guide.confs;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.confs.SysConfigDO;
import xyz.spc.infra.mapper.Guide.confs.SysConfigMapper;

@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigDO> implements SysConfigService {
}
