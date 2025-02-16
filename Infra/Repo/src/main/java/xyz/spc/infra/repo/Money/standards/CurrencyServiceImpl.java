package xyz.spc.infra.repo.Money.standards;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Money.standards.CurrencyDO;
import xyz.spc.infra.mapper.Money.standards.CurrencyMapper;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl extends ServiceImpl<CurrencyMapper, CurrencyDO> implements CurrencyService {
}
