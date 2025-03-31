package xyz.spc.serve.guest.func.levels;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.levels.LevelDO;
import xyz.spc.gate.vo.Guest.levels.LevelVO;
import xyz.spc.infra.special.Guest.levels.LevelsRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelFunc {

    /**
     * Repo
     */
    private final LevelsRepo levelsRepo;


    public LevelVO getLevelInfo(Long id) {
        // 利用MPJLambdaWrapper进行伪联表查询, 实际上是用这个实现了一次隐式类型转换 LevelDO -> LevelVO (将错就错)
        return levelsRepo.levelMapper.selectJoinOne(LevelVO.class, new MPJLambdaWrapper<LevelDO>()
                .selectAll(LevelDO.class)
                .eq(LevelDO::getId, id));
    }

    public LevelVO getLevelInfoByFloor(Long floor) {
        //同上, 偷懒直接返回VO了
        return levelsRepo.levelMapper.selectJoinOne(LevelVO.class, new MPJLambdaWrapper<LevelDO>()
                .selectAll(LevelDO.class)
                .eq(LevelDO::getFloor, floor));
    }
}
