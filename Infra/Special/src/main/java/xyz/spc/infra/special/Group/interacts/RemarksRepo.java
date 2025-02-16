package xyz.spc.infra.special.Group.interacts;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.interacts.RemarkMapper;
import xyz.spc.infra.repo.Group.interacts.RemarkService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class RemarksRepo {

    public final RemarkService remarkService;
    public final RemarkMapper remarkMapper;
}
