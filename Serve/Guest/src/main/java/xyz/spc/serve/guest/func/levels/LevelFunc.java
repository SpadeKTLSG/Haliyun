package xyz.spc.serve.guest.func.levels;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.constant.DelEnum;
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

    /**
     * 初始化用户等级 (用户注册时调用)
     * ? note 其实可以写死的, 但是为了扩展性, 还是写成方法查询
     */
    public Long initialUserLevel(Long userId) {

        // ? note 这里传入 userId, 并且是还没有生成对应用户的空置id, 是为了未来去预先查询特殊的注册用户,
        // ? note, 这样可以对一些特殊名单的用户, 包括是使用邀请码啥的进行处理. 因此留了空挡

        log.debug("用户注册, 生成等级表, userId: {}", userId);

        // 查找 等级 为 0 的等级表, 返回对应的等级 id
        LevelDO one = levelsRepo.levelService.getOne(Wrappers.lambdaQuery(LevelDO.class)
                .eq(LevelDO::getFloor, 0L)
                .eq(LevelDO::getDelFlag, DelEnum.NORMAL.getStatusCode())
        );

        return one.getId();

    }

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
