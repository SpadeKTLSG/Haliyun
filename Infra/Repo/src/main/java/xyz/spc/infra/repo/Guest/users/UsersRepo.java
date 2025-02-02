package xyz.spc.infra.repo.Guest.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户Repo
 */
@Service
@Data
@RequiredArgsConstructor
public class UsersRepo {


    public final UserService userService;

}
