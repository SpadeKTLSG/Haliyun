package xyz.spc.serve.data.func.files;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Data.files.FileDO;
import xyz.spc.domain.dos.Data.files.FileDetailDO;
import xyz.spc.domain.dos.Data.files.FileFuncDO;
import xyz.spc.domain.model.Data.files.FileFunc;
import xyz.spc.gate.dto.Data.files.FileDTO;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
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
    public Long createFile(FileDTO tmp, long size) {

        //插入三张表
        Long file_id = SnowflakeIdUtil.nextId();


        // File
        FileDO fileDO = FileDO.builder()
                .id(file_id)
                .pid(file_id) // 直接兼容
                .userId(tmp.getUserId())
                .clusterId(tmp.getClusterId())
                .name(tmp.getName())
                .type(tmp.getType())
                .build();

        filesRepo.fileService.save(fileDO);

        // FileDetail
        FileDetailDO fileDetailDO = FileDetailDO.builder()
                .id(file_id)
                .dscr("用户上传文件") // 描述
                .downloadTime(0L)
                .size(size) //大小
                .path("") // 废弃字段 : 路径
                .diskPath("")  // 废弃字段 : HDFS路径
                .build();

        filesRepo.fileDetailService.save(fileDetailDO);


        // FileFunc
        FileFuncDO fileFuncDO = FileFuncDO.builder()
                .id(file_id)
                .tag(0L) // 文件标签
                .fileLock(0L) // 文件锁
                .status(FileFunc.STATUS_NORMAL) // 文件状态: 正常
                .validDateType(FileFunc.VALID_DATE_TYPE_FOREVER) // 有效期类型: 永久有效
                .validDate(null) // 有效期: null
                .build();

        filesRepo.fileFuncService.save(fileFuncDO);

        // 返回 File id, 用于在批处理后台读取进行 HDFS 上传
        return file_id;
    }

    /**
     * 用id 获取三张表信息
     */
    public FileGreatVO getFileInfo(Long fileId) {

        // File
        FileDO fileDO = filesRepo.fileMapper.selectById(fileId);

        // FileDetail
        FileDetailDO fileDetailDO = filesRepo.fileDetailMapper.selectById(fileId);

        // FileFunc
        FileFuncDO fileFuncDO = filesRepo.fileFuncMapper.selectById(fileId);

        if (fileDO == null || fileDetailDO == null || fileFuncDO == null) {
            return null;
        }



        return FileGreatVO.builder()
                .id(fileDO.getId())
                .pid(fileDO.getPid())
                .userId(fileDO.getUserId())
                .clusterId(fileDO.getClusterId())
                .name(fileDO.getName())
                .type(fileDO.getType())
                .dscr(fileDetailDO.getDscr())
                .downloadTime(fileDetailDO.getDownloadTime())
                .size(fileDetailDO.getSize())
                .path(fileDetailDO.getPath())
                .diskPath(fileDetailDO.getDiskPath())
                .tag(fileFuncDO.getTag())
                .fileLock(fileFuncDO.getFileLock())
                .status(fileFuncDO.getStatus())
                .validDateType(fileFuncDO.getValidDateType())
                .validDate(fileFuncDO.getValidDate())
                .build();
    }
}
