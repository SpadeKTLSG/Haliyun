package xyz.spc.domain.repo.Guest.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guest.users.UserService;

/**
 * 用户Repo
 */
@Service
@RequiredArgsConstructor
public class UsersRepo {


    public final UserService userService;

}
