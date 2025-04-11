package xyz.spc.serve.guest.flow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.domain.dos.Guest.messages.SelfMailDO;
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

                            // 信件信息
                            selfMailVO.setHeader(selfMailDO.getHeader());
                            selfMailVO.setBody(selfMailDO.getBody());

                            // 信件状态
                            selfMailVO.setStatus(selfMailDO.getStatus());
                            selfMailVO.setDrop(selfMailDO.getDrop());


                            return selfMailVO;
                        }
                ).toList();



        return res;
    }

    public SelfMailVO getMyMesDetail(@NonNull Long mesId, @NonNull Integer orderType) {
    }


    /**
     * 查看消息详情
     */
    //        // 5 逻辑修改 tmp 的对象为字段更新后的结果
    //        // 6 (异步) 批量邮件Status字段状态更新 (区分类型)
    //        selfMailFunc.updateMesStatus();
}
