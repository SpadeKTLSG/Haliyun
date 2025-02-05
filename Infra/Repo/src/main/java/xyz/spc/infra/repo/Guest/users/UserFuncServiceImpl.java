package xyz.spc.infra.repo.Guest.users;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserFuncDO;
import xyz.spc.infra.mapper.Guest.users.UserFuncMapper;

@Service
@RequiredArgsConstructor
public class UserFuncServiceImpl extends ServiceImpl<UserFuncMapper, UserFuncDO> implements UserFuncService {
}
