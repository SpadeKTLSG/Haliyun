package xyz.spc.infra.special.Group.dispatchers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.dispatchers.FileStatusMapper;
import xyz.spc.infra.repo.Group.dispatchers.FileStatusService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class FileStatussRepo {

    public final FileStatusService fileStatusService;
    public final FileStatusMapper fileStatusMapper;
}
