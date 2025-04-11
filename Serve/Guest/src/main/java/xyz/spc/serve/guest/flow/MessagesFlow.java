package xyz.spc.serve.guest.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.messages.SelfMailDO;
import xyz.spc.gate.vo.Guest.messages.SelfMailVO;
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

    //Func
    private final UsersFunc usersFunc;
    private final SelfMailFunc selfMailFunc;




    /**
     * 查看我的消息列表
     */
    public List<SelfMailVO> listMyMes(Integer orderType) {

        // 1 获取用户ID
        Long userId = UserContext.getUI();


        // 2 查询 发件箱 / 收件箱 原生字段
        List<SelfMailDO> tmp = selfMailFunc.listMyMes(userId, orderType);

        // 3 补充 群组 名称


        // 4 补充 发件人 + 收件人 名称


        // 5 (异步) 邮件Status字段状态更新
    }
}
