package xyz.spc.serve.guest.func.messages;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.funcpack.exception.ServiceException;
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
     * 批量更新邮件状态为指定状态
     */
    @Async
    public void updateMesStatus(List<SelfMailDO> selfMailDOS, Integer targetStatus, Integer orderType) {

        //1 将需要更新的邮件状态设置为目标状态

        //2 加入判断: 对应状态不能回滚. 区分发件收件
        selfMailDOS.forEach(

                selfMailDO -> {

                    if (orderType == 0) {
                        // 0 收件箱, 可选值只能修改为 已查看 || 已投递未查看
                        if (targetStatus != SelfMail.STATUS_READ && targetStatus != SelfMail.STATUS_DELIVER) {
                            throw new ServiceException(ServerError.SERVICE_ILLEGAL_PARAM_ERROR);
                        }
                        selfMailDO.setStatus(targetStatus);

                    } else if (orderType == 1) {
                        // 1 发件箱, 可选值只能修改为 创建未发送 || 已发送未投递
                        if (targetStatus != SelfMail.STATUS_SEND && targetStatus != SelfMail.STATUS_CREATE) {
                            throw new ServiceException(ServerError.SERVICE_ILLEGAL_PARAM_ERROR);
                        }
                        selfMailDO.setStatus(targetStatus);

                    } else {
                        throw new ServiceException(ServerError.SERVICE_ILLEGAL_ERROR);
                    }

                }
        );

        // 3 批量更新
        selfMailsRepo.selfMailService.updateBatchById(selfMailDOS);

    }


    /**
     * 查看我的消息列表
     * ? 这个怎么说, 很贴近现实的情况 (你就把他真的当做一封信件就好了)
     *
     * @param userId    用户ID
     * @param orderType 0 收件箱 1 发件箱
     */
    public List<SelfMailDO> listMyMes(Long userId, Integer orderType) {

        List<SelfMailDO> res = null;


        switch (orderType) {

            case 0 -> {
                // 收件箱
                res = selfMailsRepo.selfMailService.list(Wrappers.lambdaQuery(SelfMailDO.class)
                        .eq(SelfMailDO::getReceiverId, userId)
                        .eq(SelfMailDO::getDrop, SelfMail.DROP_NO)

                        // 收件人只能看到状态为已经投递过来的以及已读的邮件
                        .in(SelfMailDO::getStatus, SelfMail.STATUS_DELIVER, SelfMail.STATUS_READ)
                        .orderByDesc(SelfMailDO::getUpdateTime) // 更新时间降序
                );
            }

            case 1 -> {
                // 发件箱
                res = selfMailsRepo.selfMailService.list(Wrappers.lambdaQuery(SelfMailDO.class)
                        .eq(SelfMailDO::getSenderId, userId)
                        .eq(SelfMailDO::getDrop, SelfMail.DROP_NO)

                        // 发件人只能看到状态为没投递的以及创建未发送的邮件
                        .in(SelfMailDO::getStatus, SelfMail.STATUS_CREATE, SelfMail.STATUS_SEND)
                        .orderByDesc(SelfMailDO::getUpdateTime) // 更新时间降序
                );
            }
        }

        return res;

    }

    /**
     * id查询自己的消息详情
     */
    public SelfMailDO getMyMesDetailById(Long mesId) {

        SelfMailDO selfMailDO = selfMailsRepo.selfMailService.getById(mesId);

        if (selfMailDO == null) {
            throw new ClientException(ClientError.USER_OBJECT_NOT_FOUND_ERROR);
        }

        return selfMailDO;
    }
}
