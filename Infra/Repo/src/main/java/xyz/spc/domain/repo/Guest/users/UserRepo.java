package xyz.spc.domain.repo.Guest.users;

import lombok.RequiredArgsConstructor;
import xyz.spc.domain.dao.Guest.users.UserDAO;

@RequiredArgsConstructor
public class UserRepo {

    private final UserDAO userDAO;

}
