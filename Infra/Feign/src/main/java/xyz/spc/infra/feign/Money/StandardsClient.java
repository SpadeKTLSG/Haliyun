package xyz.spc.infra.feign.Money;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.Result;
import xyz.spc.domain.dos.Money.standards.CurrencyDO;

@FeignClient(name = "money-app")
public interface StandardsClient {

    String BASE_URL = "/Money/standards";

    @GetMapping(BASE_URL + "/currency")
    Result<CurrencyDO> getCurrencyById(@RequestParam Long currencyId);
}
