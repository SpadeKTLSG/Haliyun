package xyz.spc.infra.special.Money.standards;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Money.standards.CurrencyMapper;
import xyz.spc.infra.repo.Money.standards.CurrencyService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class StandardsRepo {

    public final CurrencyService currencyService;
    public final CurrencyMapper currencyMapper;
}
