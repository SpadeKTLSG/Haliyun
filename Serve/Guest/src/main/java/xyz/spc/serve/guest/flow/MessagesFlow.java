package xyz.spc.serve.guest.flow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.domain.dos.Guest.messages.SelfMailDO;
import xyz.spc.domain.model.Guest.messages.SelfMail;
import xyz.spc.gate.dto.Guest.messages.SelfMailDTO;
import xyz.spc.gate.vo.Guest.messages.SelfMailVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.infra.feign.Guest.UsersClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.guest.func.messages.SelfMailFunc;
import xyz.spc.serve.guest.func.users.UsersFunc;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagesFlow {

    //Feign
    private final UsersClient usersClient;
    private final ClustersClient clustersClient;

    //Func
    private final UsersFunc usersFunc;
    private final SelfMailFunc selfMailFunc;


    /**
     * 查看我的消息列表
     */
    public List<SelfMailVO> listMyMes(Integer orderType) {

        // 1 获取用户ID, 校验入参
        Long userId = UserContext.getUI();

        if (orderType != 0 && orderType != 1) {
            throw new ServiceException(ServerError.SERVICE_ERROR);
        }


        // 2 查询 发件箱 / 收件箱 原生字段
        List<SelfMailDO> tmp = selfMailFunc.listMyMes(userId, orderType);


        // 3 补充 群组名称
        List<Long> clusterIds = tmp.stream()
                .map(SelfMailDO::getClusterId)
                .toList();

        List<String> clusterNamesByIds = clustersClient.getClusterNamesByIds(clusterIds);


        // 4 补充 发件人 + 收件人 名称
        List<Long> userIds = tmp.stream()
                .map(SelfMailDO::getSenderId)
                .toList();

        List<String> userAccountByIds = usersFunc.getUserAccountByIds(userIds);


        // 5 组装, 拼装 Tmodel
        List<SelfMailVO> res = tmp.stream()
                .map(
                        selfMailDO -> {
                            SelfMailVO selfMailVO = new SelfMailVO();

                            selfMailVO.setId(selfMailDO.getId());

                            // 发件者信息
                            selfMailVO.setSenderId(selfMailDO.getSenderId());
                            selfMailVO.setSenderName(userAccountByIds.get(userIds.indexOf(selfMailDO.getSenderId())));

                            // 收件者信息
                            selfMailVO.setReceiverId(selfMailDO.getReceiverId());
                            selfMailVO.setReceiverName(userAccountByIds.get(userIds.indexOf(selfMailDO.getReceiverId())));

                            // 群组信息
                            selfMailVO.setClusterId(selfMailDO.getClusterId());
                            selfMailVO.setClusterName(clusterNamesByIds.get(clusterIds.indexOf(selfMailDO.getClusterId())));

                            // 信件信息 : 列表无需展示 Body 内容
                            selfMailVO.setHeader(selfMailDO.getHeader());

                            // 信件状态
                            selfMailVO.setStatus(selfMailDO.getStatus());
                            selfMailVO.setDrop(selfMailDO.getDrop());


                            return selfMailVO;
                        }
                ).toList();


        return res;
    }


    /**
     * 查看消息详情
     */
    public SelfMailVO getMyMesDetail(Long mesId, Integer orderType) {

        // 1 查询原生字段
        SelfMailDO tmp = selfMailFunc.getMyMesDetailById(mesId);

        // 2 补充 群组名称 (复用)
        Long clusterId = tmp.getClusterId();
        List<Long> clusterIds = List.of(clusterId);

        String clusterName = clustersClient.getClusterNamesByIds(clusterIds).get(0);

        // 3 补充 发件人 + 收件人 名称  (复用)
        Long senderId = tmp.getSenderId();
        Long receiverId = tmp.getReceiverId();
        List<Long> userIds = List.of(senderId, receiverId);

        List<String> userAccountByIds = usersFunc.getUserAccountByIds(userIds);
        String senderName = userAccountByIds.get(userIds.indexOf(senderId));
        String receiverName = userAccountByIds.get(userIds.indexOf(receiverId));


        // 4 组装, 拼装 Tmodel
        SelfMailVO res = SelfMailVO.builder()
                .id(tmp.getId())

                // 发件者信息
                .senderId(senderId)
                .senderName(senderName)

                // 收件者信息
                .receiverId(receiverId)
                .receiverName(receiverName)

                // 群组信息
                .clusterId(clusterId)
                .clusterName(clusterName)

                // 信件信息
                .header(tmp.getHeader())
                .body(tmp.getBody())

                // 信件状态
                .status(tmp.getStatus())
                .drop(tmp.getDrop())

                .build();


        // 5 逻辑修改 tmp 的对象为字段更新后的结果
        if (orderType == 1 && tmp.getStatus().equals(SelfMail.STATUS_DELIVER)) {
            tmp.setStatus(SelfMail.STATUS_READ); //设置已阅
        }

        // 6 (异步) 反向批量邮件Status字段状态更新对应落库 (区分 orderType)
        List<SelfMailDO> selfMailDOS = List.of(tmp);
        selfMailFunc.updateMesStatusSpecial(selfMailDOS, null, orderType);

        return res;
    }


    /**
     * 发送消息 (创建对应的行, 通过字段标记消息状态)
     */
    public void sendMes(@NonNull SelfMailDTO selfMailDTO) {

        // 1 鉴权判断非空 (直接解包模式)
        //? 这里可以简单点鉴权, 直接把需要的字段拿出来判断

        // 2 创建对应行, 初始状态

        // 组装 DO

        // 执行

        // 3 返回用户成功 (解耦)

        // 4 (异步) 投递消息, 更新状态

    }


    /**
     * 获取用户下的未读消息数量 (收件人为自己, 并且状态为未读)
     */
    public Integer getUnreadCount() {

        // 1 获取用户ID
        Long userId = UserContext.getUI();

        // 2 查出数量
        Integer count = selfMailFunc.getUnreadCount(userId);

        return count;
    }


    /**
     * 删除消息 (双方对等 - 通过状态字段查询来实现) 默认仅收件人和发件人可操作
     */
    public void deleteMes(Long mesId) {

        // 1 查询原生字段 消息
        SelfMailDO tmp = selfMailFunc.getMyMesDetailById(mesId);

        // 2 业务鉴权
        if(tmp.getSenderId().equals(UserContext.getUI()) || tmp.getReceiverId().equals(UserContext.getUI())) {
            throw new ClientException(ClientError.USER_OBJECT_NOT_FOUND_ERROR);
        }

        // 3 删除消息 - 直接删除, 无逻辑删除必要
        selfMailFunc.deleteMesById(mesId);

    }
}
