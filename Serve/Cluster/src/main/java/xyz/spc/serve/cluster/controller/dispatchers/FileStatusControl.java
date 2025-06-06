package xyz.spc.serve.cluster.controller.dispatchers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.cluster.flow.FunctionsFlow;

@Slf4j
@MLog
@Tag(name = "Clusters", description = "群组调度合集")
@RequestMapping("/Cluster/dispatchers")
@RestController
@RequiredArgsConstructor
public class FileStatusControl {

    // Flow
    private final FunctionsFlow functionsFlow;


    //! Client


    //! Func


    //! ADD


    //! DELETE


    //! UPDATE

    //! Query
}
