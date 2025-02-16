package xyz.spc.infra.repo.Data.files;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.files.FileFuncDO;
import xyz.spc.infra.mapper.Data.files.FileFuncMapper;

@Service
@RequiredArgsConstructor
public class FileFuncServiceImpl extends ServiceImpl<FileFuncMapper, FileFuncDO> implements FileFuncService {
}
