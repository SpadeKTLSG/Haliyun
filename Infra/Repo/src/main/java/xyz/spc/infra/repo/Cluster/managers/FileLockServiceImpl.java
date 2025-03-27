package xyz.spc.infra.repo.Cluster.managers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.managers.FileLockDO;
import xyz.spc.infra.mapper.Cluster.managers.FileLockMapper;

@Service
@RequiredArgsConstructor
public class FileLockServiceImpl extends ServiceImpl<FileLockMapper, FileLockDO> implements FileLockService {
}
