package xyz.spc.infra.repo.Money.events;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Money.events.OutletDO;
import xyz.spc.infra.mapper.Money.events.OutletMapper;

@Service
@RequiredArgsConstructor
public class OutletServiceImpl extends ServiceImpl<OutletMapper, OutletDO> implements OutletService {
}
