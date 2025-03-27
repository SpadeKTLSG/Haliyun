package xyz.spc.infra.special.Cluster.dispatchers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Cluster.dispatchers.FileStatusMapper;
import xyz.spc.infra.repo.Cluster.dispatchers.FileStatusService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class FileStatussRepo {

    public final FileStatusService fileStatusService;
    public final FileStatusMapper fileStatusMapper;
}
