package xyz.spc.infra.special.Data.files;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Data.files.FileDetailMapper;
import xyz.spc.infra.mapper.Data.files.FileFuncMapper;
import xyz.spc.infra.mapper.Data.files.FileMapper;
import xyz.spc.infra.repo.Data.files.FileDetailService;
import xyz.spc.infra.repo.Data.files.FileFuncService;
import xyz.spc.infra.repo.Data.files.FileService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class FilesRepo {

    public final FileService fileService;
    public final FileMapper fileMapper;
    public final FileDetailService fileDetailService;
    public final FileDetailMapper fileDetailMapper;
    public final FileFuncService fileFuncService;
    public final FileFuncMapper fileFuncMapper;
}
