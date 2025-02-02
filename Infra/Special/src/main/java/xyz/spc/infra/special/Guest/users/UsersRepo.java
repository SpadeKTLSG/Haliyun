package xyz.spc.infra.special.Guest.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.infra.repo.Guest.users.UserService;

/**
 * 用户Repo
 */
@Service
@Data
@RequiredArgsConstructor
public class UsersRepo {


    public final UserService userService;

}
