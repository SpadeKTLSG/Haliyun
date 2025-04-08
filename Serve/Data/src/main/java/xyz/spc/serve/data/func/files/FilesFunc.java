package xyz.spc.serve.data.func.files;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Data.files.FileDO;
import xyz.spc.gate.dto.Data.files.FileDTO;
import xyz.spc.infra.special.Data.files.FilesRepo;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesFunc {

    /**
     * Repo
     */
    private final FilesRepo filesRepo;
    private final HdfsRepo hdfsRepo;

    /**
     * 根据ids批量查询文件
     */
    public List<FileDO> getFileByIdBatch(List<Long> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) {
            return List.of();
        }
        return filesRepo.fileMapper.selectBatchIds(fileIds);
    }

    /**
     * 确认 HDFS 的存活
     */
    public boolean isHDFSAlive() {
        return hdfsRepo.isHDFSAlive();
    }

    /**
     * 创建文件对象并落库
     */
    public Long createFile(FileDTO tmp) {

        Long file_id = SnowflakeIdUtil.nextId();

        FileDO res = FileDO.builder()
                .id(file_id)
                .userId(tmp.getUserId())
                .clusterId(tmp.getClusterId())
                .name(tmp.getName())
                .type(tmp.getType())
                .build();

        filesRepo.fileService.save(res);

        return file_id;
    }
}
