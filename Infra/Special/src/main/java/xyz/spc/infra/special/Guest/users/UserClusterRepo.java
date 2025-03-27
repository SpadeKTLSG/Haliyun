package xyz.spc.infra.special.Guest.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guest.users.UserClusterMapper;
import xyz.spc.infra.repo.Guest.users.UserClusterService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class UserClusterRepo {

    public final UserClusterService userClusterService;
    public final UserClusterMapper userClusterMapper;
}
