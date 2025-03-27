package xyz.spc.infra.repo.Cluster.dispatchers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.dispatchers.FileStatusDO;
import xyz.spc.infra.mapper.Cluster.dispatchers.FileStatusMapper;

@Service
@RequiredArgsConstructor
public class FileStatusServiceImpl extends ServiceImpl<FileStatusMapper, FileStatusDO> implements FileStatusService {
}
