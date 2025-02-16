package xyz.spc.infra.special.Group.functions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.functions.GroupLevelPrefixMapper;
import xyz.spc.infra.repo.Group.functions.GroupLevelPrefixService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class GroupLevelPrefixsRepo {

    public final GroupLevelPrefixService groupLevelPrefixsService;
    public final GroupLevelPrefixMapper groupLevelPrefixsMapper;
}
