package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.files.FileDO;
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
      return  filesFunc.isHDFSAlive();
    }
}
