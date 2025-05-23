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
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
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
    public void updateMesStatusSpecial(List<SelfMailDO> selfMailDOS, Integer targetStatus, Integer orderType) {

        //1 将需要更新的邮件状态设置为目标状态 (如果没传参tS, 就不执行下面的判断逻辑)
        if (targetStatus != null) {

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
        } // 没有传参的时候直接更新为对应的即可 (id查)

        // 2 批量更新
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
                        .eq(SelfMailDO::getDroped, SelfMail.DROPED_NO)

                        // 收件人只能看到状态为已经投递过来的以及已读的邮件
                        .in(SelfMailDO::getStatus, SelfMail.STATUS_DELIVER, SelfMail.STATUS_READ)
                        .orderByDesc(SelfMailDO::getUpdateTime) // 更新时间降序
                );
            }

            case 1 -> {
                // 发件箱
                res = selfMailsRepo.selfMailService.list(Wrappers.lambdaQuery(SelfMailDO.class)
                        .eq(SelfMailDO::getSenderId, userId)
                        .eq(SelfMailDO::getDroped, SelfMail.DROPED_NO)

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


    /**
     * 获取用户下的未读消息数量 (收件人为自己, 且状态为未读)
     */
    public Integer  getUnreadCount(Long userId) {

        long res = selfMailsRepo.selfMailService.count(Wrappers.lambdaQuery(SelfMailDO.class)
                .eq(SelfMailDO::getReceiverId, userId)
                .eq(SelfMailDO::getDroped, SelfMail.DROPED_NO)
                .eq(SelfMailDO::getStatus, SelfMail.STATUS_DELIVER));

        return (int) res;
    }

    /**
     * id 删除对应消息行 (无逻辑删除)
     */
    public void deleteMesById(Long mesId) {
        // 1 删除
        boolean res = selfMailsRepo.selfMailService.removeById(mesId);

        // 2 判断
        if (!res) {
            throw new ClientException(ClientError.USER_OBJECT_NOT_FOUND_ERROR);
        }
    }

    /**
     * 写入消息 (未投递)
     * ?note, 这种交互模式其实不太好, 因为直接就把 DO 在 Flow 制作了. 最好还是进行流调用
     */
    public Long writeMes(SelfMailDO selfMailDO) {

        // 1 补充 id
        Long id = SnowflakeIdUtil.nextId();
        selfMailDO.setId(id);

        // 2 写入
        selfMailsRepo.selfMailService.save(selfMailDO);

        // 3 返回id
        return id;
    }
}
