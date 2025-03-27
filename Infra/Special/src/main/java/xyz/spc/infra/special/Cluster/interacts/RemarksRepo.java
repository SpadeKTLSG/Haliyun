package xyz.spc.infra.special.Cluster.interacts;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Cluster.interacts.RemarkMapper;
import xyz.spc.infra.repo.Cluster.interacts.RemarkService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class RemarksRepo {

    public final RemarkService remarkService;
    public final RemarkMapper remarkMapper;
}
