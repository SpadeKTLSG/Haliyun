package xyz.spc.serve.guest.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.feign.Guest.UsersClient;
import xyz.spc.serve.guest.func.records.StatisticFunc;
import xyz.spc.serve.guest.func.records.TombFunc;
import xyz.spc.serve.guest.func.users.UsersFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagesFlow {

    //Feign
    private final UsersClient usersClient;

    //Func
    private final UsersFunc usersFunc;
    private final TombFunc tombFunc;
    private final StatisticFunc statisticFunc;

}
