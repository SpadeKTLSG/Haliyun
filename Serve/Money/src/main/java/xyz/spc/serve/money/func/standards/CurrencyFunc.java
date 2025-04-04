package xyz.spc.serve.money.func.standards;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Money.standards.CurrencyDO;
import xyz.spc.infra.special.Money.standards.CurrencyRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyFunc {

    private final CurrencyRepo currencyRepo;

    public CurrencyDO getCurrencyById(Long currencyId) {
        return currencyRepo.currencyService.getById(currencyId);
    }
}
