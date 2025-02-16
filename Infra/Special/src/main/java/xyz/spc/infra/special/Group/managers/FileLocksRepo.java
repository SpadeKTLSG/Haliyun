package xyz.spc.infra.special.Group.managers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.managers.FileLockMapper;
import xyz.spc.infra.repo.Group.managers.FileLockService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class FileLocksRepo {

    public final FileLockService fileLockService;
    public final FileLockMapper fileLockMapper;
}
