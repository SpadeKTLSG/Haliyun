package xyz.spc.serve.guest.func.records;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.common.constant.DelEnum;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Guest.records.TombDO;
import xyz.spc.infra.special.Guest.records.TombsRepo;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TombFunc {

    /**
     * Repo
     */
     private final TombsRepo tombsRepo;

    /**
     * 注册坟墓表, 做有趣事情的记录
     * ? quit : Tomb 部分内容只是添头, 并无开发打算
     * ? warn: 此时 TL 里面是没有信息的, 还没注册
     */
    @Async
    public void registerTomb(Long userId) {

        Long id = SnowflakeIdUtil.nextId();

        TombDO tombDO = TombDO.builder()
                .id(id)
                .userId(userId)
                .createNo(0L) // 注册时的用户排名
                .maxCoin(0L) // 最大硬币数量 (总和转义)
                .bigintestCluster(0L) // 待过最久的群 (最大)
                .deepestNignt(LocalDateTime.now()) // 最深的夜晚 (登陆)
                .earlistMorning(LocalDateTime.now()) // 最早的早晨 (登陆)
                .build();

        tombsRepo.tombService.save(tombDO);
    }

    /**
     * 删除用户坟墓表 - 更新 逻辑删除
     */
    public void killUserAccountTomb(Long userId) {

        // 1 查找
        TombDO tombDO =  Optional.of(tombsRepo.tombService.getOne(
                Wrappers.lambdaQuery(TombDO.class)
                        .eq(TombDO::getUserId, userId)
        )).orElseThrow(
                () -> new ServiceException(ServerError.SERVICE_RESOURCE_ERROR)
        );

        // 2 更新
        tombDO.setDelFlag(DelEnum.DELETE.getStatusCode());
        tombsRepo.tombService.updateById(tombDO);
    }
}
