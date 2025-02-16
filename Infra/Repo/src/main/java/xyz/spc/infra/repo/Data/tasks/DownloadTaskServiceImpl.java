package xyz.spc.infra.repo.Data.tasks;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.tasks.DownloadTaskDO;
import xyz.spc.infra.mapper.Data.tasks.DownloadTaskMapper;

@Service
@RequiredArgsConstructor
public class DownloadTaskServiceImpl extends ServiceImpl<DownloadTaskMapper, DownloadTaskDO> implements DownloadTaskService {
}
