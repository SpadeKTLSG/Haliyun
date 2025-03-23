package xyz.spc.serve.guest.controller.levels;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.gate.vo.Guest.levels.LevelVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.guest.flow.LevelsFlow;

@Slf4j
@MLog
@Tag(name = "Levels", description = "用户等级合集")
@RequestMapping("/Guest/levels")
@RestController
@RequiredArgsConstructor
public class LevelsControl {

    // Flow
    private final LevelsFlow levelsFlow;


    //! Func

    //! ADD
    // 管理员手填

    //! DELETE
    // 管理员手填

    //! UPDATE
    // 管理员手填


    //! Query

    /**
     * id获取等级信息
     */
    @GetMapping("/levelinfo")
    @Operation(summary = "查等级信息")
    @Parameter(name = "id", description = "等级id", required = true)
    public LevelVO getLevelInfo(@NotNull @RequestParam("id") Long id) {
        return levelsFlow.getLevelInfo(id);
    }
    //http://localhost:10000/Guest/levels/levelinfo?id=...


    /**
     * floor获取等级信息
     */
    @GetMapping("/levelinfo/floor")
    @Operation(summary = "用楼层查等级信息")
    @Parameter(name = "floor", description = "楼层", required = true)
    public LevelVO getLevelInfoByFloor(@NotNull @RequestParam("floor") Integer floor) {
        return levelsFlow.getLevelInfoByFloor(floor);
    }
    //http://localhost:10000/Guest/levels/levelinfo/floor?floor=...
}
