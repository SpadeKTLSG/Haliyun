package xyz.spc.infra.repo.Guest.users;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserDetailDO;
import xyz.spc.infra.mapper.Guest.users.UserDetailMapper;

@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetailDO> implements UserDetailService {
}
