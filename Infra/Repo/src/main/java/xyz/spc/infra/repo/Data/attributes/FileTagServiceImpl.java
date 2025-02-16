package xyz.spc.infra.repo.Data.attributes;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.attributes.FileTagDO;
import xyz.spc.infra.mapper.Data.attributes.FileTagMapper;

@Service
@RequiredArgsConstructor
public class FileTagServiceImpl extends ServiceImpl<FileTagMapper, FileTagDO> implements FileTagService {
}
