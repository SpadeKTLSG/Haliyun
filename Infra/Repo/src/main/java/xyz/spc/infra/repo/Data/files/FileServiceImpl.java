package xyz.spc.infra.repo.Data.files;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.files.FileDO;
import xyz.spc.infra.mapper.Data.files.FileMapper;

@Service
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDO> implements FileService {
}
