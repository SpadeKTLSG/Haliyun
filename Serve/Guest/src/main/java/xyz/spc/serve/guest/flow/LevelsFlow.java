package xyz.spc.serve.guest.flow;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.gate.vo.Guest.levels.LevelVO;
import xyz.spc.infra.feign.Guest.LevelsClient;
import xyz.spc.serve.guest.func.levels.LevelFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelsFlow {

    //Feign
    private final LevelsClient levelsClient;

    //Func
    private final LevelFunc levelFunc;

    /**
     * 初始化用户等级 (用户注册时调用)
     */
    public void initialUserLevel(Long userId) {
        levelFunc.initialUserLevel(userId);
    }

    /**
     * 获取等级信息
     */
    public LevelVO getLevelInfo(Long id) {
        return levelFunc.getLevelInfo(id);
    }

    /**
     * 通过楼层获取等级信息
     */
    public LevelVO getLevelInfoByFloor(@NotNull Long floor) {
        return levelFunc.getLevelInfoByFloor(floor);
    }
}
