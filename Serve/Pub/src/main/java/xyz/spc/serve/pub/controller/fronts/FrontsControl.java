package xyz.spc.serve.pub.controller.fronts;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Pub.fronts.MenuVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.pub.flow.fronts.FrontsFlow;

import java.util.List;

@Slf4j
@MLog
@Tag(name = "Users", description = "公共前端合集")
@RequestMapping("/Pub/fronts")
@RestController
@RequiredArgsConstructor
public class FrontsControl {

    // Flow
    private final FrontsFlow frontsFlow;

    /**
     * 获取菜单 todo: 未完成权限改造
     */
    @GetMapping("/nav")
    @Operation(summary = "获取菜单")
    public Result<List<MenuVO>> getBasicNav() {
        return Result.success(frontsFlow.listNav());
    }
    //http://localhost:10000/Pub/fronts/nav

}
