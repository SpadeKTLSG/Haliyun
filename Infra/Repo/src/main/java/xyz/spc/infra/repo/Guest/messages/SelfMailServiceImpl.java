package xyz.spc.infra.repo.Guest.messages;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.messages.SelfMailDO;
import xyz.spc.infra.mapper.Guest.messages.SelfMailMapper;

@Service
@RequiredArgsConstructor
public class SelfMailServiceImpl extends ServiceImpl<SelfMailMapper, SelfMailDO> implements SelfMailService {
}
