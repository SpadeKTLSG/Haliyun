package xyz.spc.serve.cluster.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.domain.dos.Cluster.managers.ClusterAuthDO;
import xyz.spc.gate.vo.Cluster.managers.ClusterAuthVO;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.infra.feign.Guest.UsersClient;
import xyz.spc.serve.cluster.func.managers.ClusterAuthFunc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagersFlow {

    //Feign
    private final UsersClient usersClient;

    //Func
    private final ClusterAuthFunc clusterAuthFunc;


    /**
     * 分页查询群组中所有用户的权限
     * (详情也是复用这个数据)
     */
    public List<ClusterAuthVO> getAllClusterAuths(Long targetClusterId, PageRequest pageRequest) {

        // 1 查分页的对应字段 (简单业务原生分页)
        List<ClusterAuthDO> clusterAuthDOList = clusterAuthFunc.getAllClusterMemberAuths(targetClusterId, pageRequest);

        // 2 补充对应 Tmodel : 用户账户

        // 2.1 归拢需要查对应账户的 userIds

        // 我稍微换了个写法, 不是 直接用 stream的
        List<Long> userIds = new ArrayList<>();
        clusterAuthDOList.forEach(clusterAuthDO -> {
            userIds.add(clusterAuthDO.getUserId());
        });

        // 2.2 批量查询对应账户的账户信息
        Result<List<UserVO>> listResult = Optional.ofNullable(usersClient.getUserDOInfoBatch(userIds))
                .orElseThrow(() -> new ServiceException(ServerError.SERVICE_RPC_ERROR));
        List<UserVO> userVOList = listResult.getData();


        // 3 转化为对应 VO
        List<ClusterAuthVO> res = new ArrayList<>();
        clusterAuthDOList.forEach(source -> {

            ClusterAuthVO clusterAuthVO = ClusterAuthVO.builder()
                    .id(source.getId())
                    // 群组默认前端带入不需要
                    .userId(source.getUserId())
                    .account(userVOList.stream()
                            .filter(userVO -> userVO.getId().equals(source.getUserId()))
                            .findFirst()
                            .orElseThrow(() -> new ServiceException(ServerError.SERVICE_RESOURCE_ERROR))
                            .getAccount())
                    .canKick(source.getCanKick())
                    .canInvite(source.getCanInvite())
                    .canUpload(source.getCanUpload())
                    .canDownload(source.getCanDownload())
                    .status(source.getStatus())
                    .build();


            res.add(clusterAuthVO);
        });

        return res;
    }
}
