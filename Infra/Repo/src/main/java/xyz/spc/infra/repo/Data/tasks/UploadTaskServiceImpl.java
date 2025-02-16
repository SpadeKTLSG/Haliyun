package xyz.spc.infra.repo.Data.tasks;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.tasks.UploadTaskDO;
import xyz.spc.infra.mapper.Data.tasks.UploadTaskMapper;

@Service
@RequiredArgsConstructor
public class UploadTaskServiceImpl extends ServiceImpl<UploadTaskMapper, UploadTaskDO> implements UploadTaskService {
}
