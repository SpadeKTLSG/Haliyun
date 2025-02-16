package xyz.spc.infra.repo.Group.managers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.managers.FileLockDO;
import xyz.spc.infra.mapper.Group.managers.FileLockMapper;

@Service
@RequiredArgsConstructor
public class FileLockServiceImpl extends ServiceImpl<FileLockMapper, FileLockDO> implements FileLockService {
}
