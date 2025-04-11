package xyz.spc.serve.guest.func.messages;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.messages.SelfMailDO;
import xyz.spc.domain.model.Guest.messages.SelfMail;
import xyz.spc.infra.special.Guest.messages.SelfMailsRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SelfMailFunc {


    //Repo
    private final SelfMailsRepo selfMailsRepo;


    /**
     * 更新邮件状态为指定状态
     *
     * @param mesId        邮件ID
     * @param targetStatus 目标状态
     */
    @Async
    public void updateMesStatus(Long mesId, Integer targetStatus) {
        selfMailsRepo.selfMailService.update(Wrappers.lambdaUpdate(SelfMailDO.class)
                .set(SelfMailDO::getStatus, targetStatus)
                .eq(SelfMailDO::getId, mesId)
        );
    }


    /**
     * 查看我的消息列表
     *
     * @param userId    用户ID
     * @param orderType 0 收件箱 1 发件箱
     */
    public List<SelfMailDO> listMyMes(Long userId, Integer orderType) {

        if (orderType.equals(0)) {
            // 收件箱
            return selfMailsRepo.selfMailService.list(Wrappers.lambdaQuery(SelfMailDO.class)
                    .eq(SelfMailDO::getReceiverId, userId)
                    // 收件人只能看到状态为已经投递过来的以及已读的邮件
                    .in(SelfMailDO::getStatus, SelfMail.STATUS_DELIVER, SelfMail.STATUS_READ)
                    .orderByDesc(SelfMailDO::getCreateTime)
            );

        } else {
            // 发件箱
            return selfMailsRepo.selfMailService.list(Wrappers.lambdaQuery(SelfMailDO.class)
                    .eq(SelfMailDO::getSenderId, userId)
                    .orderByDesc(SelfMailDO::getCreateTime)
            );
        }

    }
}
