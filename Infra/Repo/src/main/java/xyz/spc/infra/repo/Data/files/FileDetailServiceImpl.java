package xyz.spc.infra.repo.Data.files;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.files.FileDetailDO;
import xyz.spc.infra.mapper.Data.files.FileDetailMapper;

@Service
@RequiredArgsConstructor
public class FileDetailServiceImpl extends ServiceImpl<FileDetailMapper, FileDetailDO> implements FileDetailService {
}
