package xyz.spc.serve.money.controller.standards;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
import xyz.spc.domain.dos.Money.standards.CurrencyDO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.money.flow.StandardsFlow;


@Slf4j
@MLog
@Tag(name = "Standards", description = "金钱规范合集")
@RequestMapping("/Money/standards")
@RestController
@RequiredArgsConstructor
public class StandardsControl {


    // Flow
    private final StandardsFlow standardsFlow;


    //! Client


    @GetMapping("/currency")
    Result<CurrencyDO> getCurrencyById(@RequestParam Long currencyId) {
        return Result.success(standardsFlow.getCurrencyById(currencyId));
    }


    //! Func


    //! DELETE


    //! ADD


    //! UPDATE


    //! Query

}
