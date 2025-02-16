package xyz.spc.serve.pub.controller.systems;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.pub.flow.systems.SystemsFlow;

@Slf4j
@MLog
@Tag(name = "Users", description = "公共系统合集")
@RequestMapping("/Pub/systems")
@RestController
@RequiredArgsConstructor
public class SystemsControl {

    // Flow
    private final SystemsFlow systemsFlow;

    @GetMapping("/nav")
    @Operation(summary = "获取菜单")
    public Result getBasicNav() {
        return Result.success(systemsFlow.listNav());
    }

}
