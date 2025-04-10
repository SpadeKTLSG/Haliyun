package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Data.files.FileDO;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.gate.vo.Data.files.FileShowVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
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

            fileShowVO.setId(file.getId());
            fileShowVO.setPid(file.getPid());
            fileShowVO.setUserId(file.getUserId());
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

        //        <el-table-column prop="name" label="文件名"></el-table-column>
        //        <el-table-column prop="type" label="文件类型"></el-table-column>
        //        <el-table-column prop="dscr" label="描述"></el-table-column>
        //        <el-table-column prop="downloadTime" label="下载次数"></el-table-column>
        //        <el-table-column prop="size" label="文件大小"></el-table-column>
        //        <el-table-column prop="status" label="状态"></el-table-column>
        //
        //        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        //        <el-table-column prop="updateTime" label="更新时间"></el-table-column>

        //1. 获取这个群组中所有文件的 ids


        //2. 根据ids + page分页信息算出需要查询的 File* ids

        //3. 根据ids 发起批量查询, 获取到需要的文件对象 * 3 => 整理到汇总 VO, 注意联系前端业务展示

        //3.1 FileDO

        //3.2 FileDetailDO

        //3.3 FileFuncDO

        //3.4 Merge into FileGreatVO

        //4. 补充 Lock / Tag / 有效期 等 传递信息 => 取消, 放到 Detail 的接口里面进行单个查询详情

        //4 将 List res 封装返回
    }
}
