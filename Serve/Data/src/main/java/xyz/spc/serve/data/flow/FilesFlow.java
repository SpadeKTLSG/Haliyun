package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Data.files.FileDO;
import xyz.spc.domain.model.Data.files.File;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.gate.vo.Data.files.FileShowVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.auxiliary.config.design.chain.AbstractChainContext;
import xyz.spc.serve.auxiliary.config.event.MyEventPublisher;
import xyz.spc.serve.data.common.enums.FilesChainMarkEnum;
import xyz.spc.serve.data.func.files.FilesFunc;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesFlow {


    //Feign
    private final ClustersClient clustersClient;

    //Func
    private final FilesFunc filesFunc;
    private final TasksFlow tasksFlow;

    /**
     * 责任链
     */
    private final AbstractChainContext<File, FileGreatVO> abstractChainContext;

    /**
     * 事件发布器
     */
    private final MyEventPublisher<String> publisher;

    /**
     * 根据id批量查询文件
     */
    public List<FileShowVO> getFileByIdBatch(List<Long> fileIds) {

        // 批量查询
        List<FileDO> fileList = filesFunc.getFileByIdBatch(fileIds);

        if (fileList == null || fileList.isEmpty()) {
            return List.of();
        }

        // 补充群组查询信息
        // 收集所有需要查询的 clusterId
        List<Long> clusterIds = fileList.stream().map(FileDO::getClusterId).toList();

        // 批量查询 clusterName
        List<String> clusterNames = clustersClient.getClusterNamesByIds(clusterIds);

        // 补充信息
        return fileList.stream().map(file -> {

            FileShowVO fileShowVO = new FileShowVO();

            fileShowVO.setId(String.valueOf(file.getId()));
            fileShowVO.setPid(String.valueOf(file.getPid()));
            fileShowVO.setUserId(String.valueOf(file.getUserId()));
            fileShowVO.setName(file.getName());

            fileShowVO.setCreateTime(file.getCreateTime());
            fileShowVO.setUpdateTime(file.getUpdateTime());

            // 补充群组名称
            int index = clusterIds.indexOf(file.getClusterId());
            if (index != -1) {
                fileShowVO.setClusterName(clusterNames.get(index));
            }

            return fileShowVO;
        }).collect(Collectors.toList());
    }

    /**
     * 获取单个文件的所有信息
     */
    public FileGreatVO getOneFileAllInfo(Long fileId) {

        // 1 查三张表
        FileGreatVO tmp = filesFunc.getFileInfo(fileId);

        // 2 补充群组查询信息
        // 收集所有需要查询的 clusterId
        List<Long> clusterIds = List.of(Long.valueOf(tmp.getClusterId()));
        // 批量查询 clusterName
        List<String> clusterNames = clustersClient.getClusterNamesByIds(clusterIds);

        // 3 补充信息
        FileGreatVO res = FileGreatVO.builder()
                .id(tmp.getId())
                .pid(tmp.getPid())
                .userId(tmp.getUserId())
                .clusterId(tmp.getClusterId())
                .name(tmp.getName())
                .type(tmp.getType())
                // Detail
                .dscr(tmp.getDscr())
                .downloadTime(tmp.getDownloadTime())
                .size(tmp.getSize())
                .path(tmp.getPath())
                .diskPath(tmp.getDiskPath())
                // Func
                .tag(tmp.getTag())
                .fileLock(tmp.getFileLock())
                .status(tmp.getStatus())
                .validDateType(tmp.getValidDateType())
                .validDate(tmp.getValidDate())
                .build();
        return res;
    }

    /**
     * 预热一下 HDFS, 获取一下看看
     */
    public boolean tryAcquireDataSystem() {
        return filesFunc.isHDFSAlive();
    }

    /**
     * 分页获取群组中的文件列表 (List)
     */
    public PageResponse<FileGreatVO> getGroupFilePage(Long clusterId, PageRequest pageRequest) {
        //分页的思路和之前还是一样, 变成手动逻辑分页, 先查出需要的ids, 然后再批量查询

        //1. 获取这个群组中所有文件的 ids
        List<Long> groupFileIds = filesFunc.getGroupFileIds(clusterId);

        //2. 根据ids + page分页信息算出需要查询的 File* ids
        // 提前进行逻辑分页操作: 将 分页查询 降级为 批量查询, 对方服务只需要查询提供的 ids
        int currentPage = pageRequest.getCurrent();
        int pageSize = pageRequest.getSize();
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, groupFileIds.size());

        // 避免 越界
        if (fromIndex >= groupFileIds.size()) {
            return new PageResponse<>(currentPage, pageSize, groupFileIds.size(), List.of());
        }

        // 获取当前页的 File id
        List<Long> pagedFileIds = groupFileIds.subList(fromIndex, toIndex);

        //3. 根据ids 发起批量查询, 获取到需要的文件对象 * 3 => 整理到汇总 VO, 注意联系前端业务展示

        //使用联表查询 Merge into FileGreatVO (没有分页)
        List<FileGreatVO> fileGreatVOS = filesFunc.queryAllFileInfoByIds(pagedFileIds);

        //4. 补充 Lock / Tag / 有效期 等 传递信息 => 取消, 放到 Detail 的接口里面进行单个查询详情

        //4 将 List res 封装返回
        return new PageResponse<>(
                pageRequest.getCurrent(),
                pageRequest.getSize(),
                groupFileIds.size(),
                fileGreatVOS
        );
    }


    /**
     * 分页获取群组中的文件列表 (List), 根据文件名模糊查询筛选对应文件
     */
    public PageResponse<FileGreatVO> getGroupFilePage8Name(Long clusterId, String fileName, PageRequest pageRequest) {

        //1. 获取这个群组中所有可能的文件的 ids (模糊匹配)
        List<Long> groupFileIds = filesFunc.getGroupFileIds8Name(clusterId, fileName);

        //? 同上

        //2. 根据ids + page分页信息算出需要查询的 File * ids
        // 提前进行逻辑分页操作: 将 分页查询 降级为 批量查询, 对方服务只需要查询提供的 ids
        int currentPage = pageRequest.getCurrent();
        int pageSize = pageRequest.getSize();
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, groupFileIds.size());

        // 避免 越界
        if (fromIndex >= groupFileIds.size()) {
            return new PageResponse<>(currentPage, pageSize, groupFileIds.size(), List.of());
        }

        // 获取当前页的 File id
        List<Long> pagedFileIds = groupFileIds.subList(fromIndex, toIndex);

        //3. 根据ids 发起批量查询, 获取到需要的文件对象 * 3 => 整理到汇总 VO, 注意联系前端业务展示

        //使用联表查询 Merge into FileGreatVO (没有分页)
        List<FileGreatVO> fileGreatVOS = filesFunc.queryAllFileInfoByIds(pagedFileIds);

        //4. 补充 Lock / Tag / 有效期 等 传递信息 => 取消, 放到 Detail 的接口里面进行单个查询详情

        //4 将 List res 封装返回
        return new PageResponse<>(
                pageRequest.getCurrent(),
                pageRequest.getSize(),
                groupFileIds.size(),
                fileGreatVOS
        );
    }


    /**
     * 上传文件失败后的分布式事务回滚: 最终一致性实现
     */
    @Async
    public void rollBackFileCreate4Failure(Long fileId) {

        try {
            // 1 直接删除文件表的记录 (因为是创建失败, 无逻辑删除)
            filesFunc.deleteFileAll(fileId);


            // 2 处理 锁 等逻辑
            // 因为是创建失败, 文件刚刚创建, 不需要处理其他的逻辑


            // 3 HDFS 无需操作, 因为没有上传成功的文件, 文件夹无所谓会定时任务清理


        } catch (Exception ignore) {
            //因为是最终一致性的异步操作, 这里不能抛出异常, 改为记录日志 + 风控日志
            log.error("上传文件失败后的分布式事务回滚 操作失败, 文件ID: {}", fileId);
        }

    }

    /**
     * 删除文件对象
     */
    public void deleteFile(Long fileId) {

        // 1.获取文件对象
        FileGreatVO fileGreatVO = filesFunc.getFileInfo(fileId);

        // 2.鉴权: 数据库层鉴权, 判别当前用户是否有权限删除这个文件对象:
        // 2.1 责任链处理 + 与业务互动 热插拔
        abstractChainContext.handler(FilesChainMarkEnum.FILE_DELETE_FILTER.name(), new File(), fileGreatVO);

        // 3. 执行删除
        filesFunc.deleteFileAll(fileId);

        // 4. 借助 Spring Event 使用 HDFS 解耦删除文件. 需要传入唯一定位文件对象的 用户id + 目标群组id

        // 4.1 构建事件对象 : 提前构建 目标路径

        String hdfsPath = "/";
        hdfsPath = hdfsPath + fileGreatVO.getUserId() + "/" + fileGreatVO.getClusterId() + "/" + fileGreatVO.getName();


        // 4.2 标识事件
        String eventMark = "deleteFileInHDFSByPath";

        // 4.3 发布事件
        publisher.publishEvent(eventMark, hdfsPath);

    }


    /**
     * 判断我是不是这个文件的所在群组的群主
     */
    public boolean amICreatorOfFileCluster(Long fileId) {

        // 1 查对应文件的信息
        FileGreatVO oneFileAllInfo = this.getOneFileAllInfo(fileId);

        // 2 找到对应群组id
        Long clusterId = Long.valueOf(oneFileAllInfo.getClusterId());

        // 3 判断我是不是这个群组的群主
        Long myUserId = UserContext.getUI();

        boolean amIMaster = clustersClient.checkClusterCreatorEqual(clusterId, myUserId);

        return amIMaster;
    }


}
