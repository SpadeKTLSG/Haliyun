package xyz.spc.serve.data.func.files;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
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

    /**
     * 获取群组中的所有正常的文件id
     */
    public List<Long> getGroupFileIds(Long clusterId) {

        if (clusterId == null) {
            return List.of();
        }

        //? note: 联表List批量查询, 使用 MPJLambdaWrapper 模拟 (这个代码风格很熟悉, 哈哈哈)
        // 但是如果只需要 id 字段, 可以简化select的列直接拿出来
/*
        List<FileDO> tmp = filesRepo.fileMapper.selectJoinList(FileDO.class,
                new MPJLambdaWrapper<FileDO>()
                        .selectAll(FileDO.class)
                        .leftJoin(FileFuncDO.class, FileFuncDO::getId, FileDO::getId)
                        .eq(FileDO::getClusterId, clusterId)
                        .eq(FileFuncDO::getStatus, FileFunc.STATUS_NORMAL)
                        .orderByDesc(FileDO::getUpdateTime)
        );
          List<Long> res = tmp.stream().map(FileDO::getId).toList();
           return res;
*/

        List<Long> res = filesRepo.fileMapper.selectJoinList(Long.class,
                new MPJLambdaWrapper<FileDO>()
                        .select(FileDO::getId)
                        .leftJoin(FileFuncDO.class, FileFuncDO::getId, FileDO::getId)
                        .eq(FileDO::getClusterId, clusterId)
                        .eq(FileFuncDO::getStatus, FileFunc.STATUS_NORMAL)
                        .orderByDesc(FileDO::getUpdateTime)
        );

        return res;
    }


    /**
     * 根据ids批量 联表查询文件
     */
    public List<FileGreatVO> queryAllFileInfoByIds(List<Long> pagedFileIds) {
        if (pagedFileIds == null || pagedFileIds.isEmpty()) {
            return List.of();
        }

        // 联表查询基操: 把需要的类型放在入参, 实际的MPJLambdaWrapper会自动推导 (放DO)
        List<FileGreatVO> res = filesRepo.fileMapper.selectJoinList(FileGreatVO.class,
                new MPJLambdaWrapper<FileDO>()
                        // 偷懒全都查了
                        .selectAll(FileDO.class)
                        .selectAll(FileDetailDO.class)
                        .selectAll(FileFuncDO.class)
                        .leftJoin(FileDetailDO.class, FileDetailDO::getId, FileDO::getId)
                        .leftJoin(FileFuncDO.class, FileFuncDO::getId, FileDO::getId)
                        .eq(FileFuncDO::getStatus, FileFunc.STATUS_NORMAL) // 只查询正常的文件
                        .in(FileDO::getId, pagedFileIds) // 批量查询
        );

        return res;
    }


    /**
     * 联表删除文件对象
     * ? note: 联表删除 -> 单个删除, 不支持三张表一起删
     */
    public void deleteFileAll(Long fileId) {

        filesRepo.fileMapper.delete(
                new MPJLambdaWrapper<>(FileDO.class)
                        .eq(FileDO::getId, fileId)
        );
        filesRepo.fileDetailMapper.delete(
                new MPJLambdaWrapper<>(FileDetailDO.class)
                        .eq(FileDetailDO::getId, fileId)
        );
        filesRepo.fileFuncMapper.delete(
                new MPJLambdaWrapper<>(FileFuncDO.class)
                        .eq(FileFuncDO::getId, fileId)
        );
    }

    /**
     * 增加用户下载次数
     * ? 自增 SQL 示例
     */
    public void addUserDownloadTimes(Long fileId) {

        filesRepo.fileDetailService.update(Wrappers.lambdaUpdate(FileDetailDO.class)
                .setSql("download_time = download_time + 1") // 使用 setSql 实现 自增
                .eq(FileDetailDO::getId, fileId) // 文件id
        );
    }


    /**
     * 更新文件File
     */
    public void updateFile(FileDO fileDO) {
        filesRepo.fileService.updateById(fileDO);
    }


    /**
     * 更新文件FileDetail
     */
    public void updateFileDetail(FileDetailDO fileDetailDO) {
        filesRepo.fileDetailService.updateById(fileDetailDO);
    }

    /**
     * 更新文件FileFunc
     */
    public void updateFileFunc(Long tagID, Long fileId) {
        filesRepo.fileFuncService.update(Wrappers.lambdaUpdate(FileFuncDO.class)
                .set(FileFuncDO::getTag, tagID)
                .eq(FileFuncDO::getId, fileId)
        );
    }


    /**
     * 通过标签id查找文件列表
     * ? note 使用 JoinList 进行联表批量查询
     */
    public List<FileDO> getFilesByTagId(Long tagId) {

        List<FileDO> res = filesRepo.fileMapper.selectJoinList(FileDO.class,
                new MPJLambdaWrapper<FileDO>()
                        .selectAll(FileDO.class)
                        .leftJoin(FileFuncDO.class, FileFuncDO::getId, FileDO::getId)
                        .eq(FileFuncDO::getTag, tagId)
                        .eq(FileFuncDO::getStatus, FileFunc.STATUS_NORMAL)
        );

        return res;
    }

}
