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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Long> userIds = new ArrayList<>();

        // 都需要添加到查询清单中
        for (SelfMailDO selfMailDO : tmp) {
            userIds.add(selfMailDO.getSenderId());
            userIds.add(selfMailDO.getReceiverId());
        }

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

                            // 群组信息 (这个可能为空)
                            selfMailVO.setClusterId(selfMailDO.getClusterId());

                            if (clusterIds.contains(selfMailDO.getClusterId()) && !clusterNamesByIds.isEmpty()) {
                                selfMailVO.setClusterName(clusterNamesByIds.get(clusterIds.indexOf(selfMailDO.getClusterId())));
                            } else {
                                selfMailVO.setClusterName("无群组上下文信息");
                            }

                            // 信件信息 : 列表无需展示 Body 内容
                            selfMailVO.setHeader(selfMailDO.getHeader());

                            // 信件状态
                            selfMailVO.setStatus(selfMailDO.getStatus());
                            selfMailVO.setDroped(selfMailDO.getDroped());


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
        String clusterName;
        List<Long> clusterIds;

        if (clusterId != null) {
            clusterIds = List.of(clusterId);
            clusterName = clustersClient.getClusterNamesByIds(clusterIds).get(0);
        }else{
            clusterName = "无群组上下文信息";
        }


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
                .droped(tmp.getDroped())

                .build();


        // 5 逻辑修改 tmp 的对象为字段更新后的结果
        if (orderType == 0 && tmp.getStatus().equals(SelfMail.STATUS_DELIVER)) {
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
        //? 这里可以简单点鉴权, 直接把需要的字段拿出来判断. 也影响了对应的下面业务方法. 是新的尝试


        // 1.1 发送者ID (默认是自己)
        Long senderId = Optional.ofNullable(selfMailDTO.getSenderId()).orElse(UserContext.getUI());

        // 1.2 收件者ID, 空报错
        Long receiverId = Optional.ofNullable(selfMailDTO.getReceiverId()).orElseThrow(
                () -> new ClientException(ClientError.USER_OBJECT_NOT_FOUND_ERROR)
        );

        // 1.3 来自群组ID.
        // note: 这里不一定要带上群组信息. 注意展示时候显示无群组即可, 只是附加的一个 Context Info


        // 2 创建对应行, 初始状态
        SelfMailDO selfMailDO = SelfMailDO.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .header(Optional.ofNullable(selfMailDTO.getHeader()).orElse("无标题"))
                .body(Optional.ofNullable(selfMailDTO.getBody()).orElse("无内容"))
                .clusterId(selfMailDTO.getClusterId()) // 这个字段目前没有特别大的作用, 容忍了
                .status(SelfMail.STATUS_SEND) // 初始状态 简单模拟, 直接跳过 (前端的中间状态)
                .droped(SelfMail.DROPED_NO) // 默认不删除
                .build();

        selfMailFunc.writeMes(selfMailDO);


        // 3 返回用户成功 (解耦)
        // 自动进行...... 然后就自动后台系统来做投递了


        // 4 (异步) 投递消息, 更新状态
        // id 已经被创建了, 直接使用 DO 更新状态即可
        selfMailFunc.updateMesStatusSpecial(List.of(selfMailDO), SelfMail.STATUS_DELIVER, 0); // 0 收件箱
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
        if (!tmp.getSenderId().equals(UserContext.getUI()) && !tmp.getReceiverId().equals(UserContext.getUI())) {
            throw new ClientException(ClientError.USER_OBJECT_NOT_FOUND_ERROR);
        }

        // 3 删除消息 - 直接删除, 无逻辑删除必要
        selfMailFunc.deleteMesById(mesId);

    }
}
