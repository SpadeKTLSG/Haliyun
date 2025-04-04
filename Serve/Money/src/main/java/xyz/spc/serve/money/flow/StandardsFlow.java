package xyz.spc.serve.money.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Money.standards.CurrencyDO;
import xyz.spc.serve.money.func.standards.CurrencyFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class StandardsFlow {


    //Feign


    //Func
    private final CurrencyFunc currencyFunc;


    /**
     * 根据id查询货币 (简单)
     */
    public CurrencyDO getCurrencyById(Long currencyId) {
        return currencyFunc.getCurrencyById(currencyId);
    }

}
