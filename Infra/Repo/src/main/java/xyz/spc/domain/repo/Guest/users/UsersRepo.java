package xyz.spc.domain.repo.Guest.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.spc.infra.mapper.Guest.users.UserService;

/**
 * 用户Repo
 */
@Repository
@RequiredArgsConstructor
public class UsersRepo {

    public final UserService userService;

}
