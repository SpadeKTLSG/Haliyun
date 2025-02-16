package xyz.spc.infra.repo.Money.donates;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Money.donates.DonateDO;
import xyz.spc.infra.mapper.Money.donates.DonateMapper;

@Service
@RequiredArgsConstructor
public class DonateServiceImpl extends ServiceImpl<DonateMapper, DonateDO> implements DonateService {
}
