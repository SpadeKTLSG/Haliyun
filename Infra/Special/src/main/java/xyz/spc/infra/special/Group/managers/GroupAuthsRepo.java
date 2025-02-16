package xyz.spc.infra.special.Group.managers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.managers.GroupAuthMapper;
import xyz.spc.infra.repo.Group.managers.GroupAuthService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class GroupAuthsRepo {

    public final GroupAuthService groupAuthService;
    public final GroupAuthMapper groupAuthMapper;
}
