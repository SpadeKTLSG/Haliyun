package xyz.spc.serve.guest.func.users;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserClusterDO;
import xyz.spc.infra.special.Guest.users.UserClusterRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserClusterFunc {

    /**
     * Repo
     */
    private final UserClusterRepo usersRepo;

    /**
     * 获取用户的群组ids
     */
    public List<Long> getUsersClusterIds(Long id) {

        List<UserClusterDO> temp = usersRepo.userClusterMapper.selectList(
                Wrappers.lambdaQuery(UserClusterDO.class)
                        .eq(UserClusterDO::getUserId, id)
                        .orderByAsc(UserClusterDO::getSort)
        );

        return temp.stream().map(UserClusterDO::getClusterId).toList();
    }
}
