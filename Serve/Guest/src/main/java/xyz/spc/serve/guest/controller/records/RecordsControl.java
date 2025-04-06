package xyz.spc.serve.guest.controller.records;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.guest.flow.RecordsFlow;

@Slf4j
@MLog
@Tag(name = "Records", description = "用户记录合集")
@RequestMapping("/Guest/records")
@RestController
@RequiredArgsConstructor
public class RecordsControl {

    // Flow
    private final RecordsFlow recordsFlow;


    //! Client


    //! Func


    //! ADD


    //! DELETE


    //! UPDATE

    //! Query
}
