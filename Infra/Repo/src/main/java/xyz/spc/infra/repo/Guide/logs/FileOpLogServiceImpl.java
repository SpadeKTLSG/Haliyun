package xyz.spc.infra.repo.Guide.logs;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guide.logs.FileOpLogDO;
import xyz.spc.infra.mapper.Guide.logs.FileOpLogMapper;

@Service
@RequiredArgsConstructor
public class FileOpLogServiceImpl extends ServiceImpl<FileOpLogMapper, FileOpLogDO> implements FileOpLogService {
}
