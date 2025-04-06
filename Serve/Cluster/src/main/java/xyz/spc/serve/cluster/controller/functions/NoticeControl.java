package xyz.spc.serve.cluster.controller.functions;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;

@Slf4j
@MLog
@Tag(name = "Clusters", description = "群组功能合集")
@RequestMapping("/Cluster/functions")
@RestController
@RequiredArgsConstructor
public class NoticeControl {




}
