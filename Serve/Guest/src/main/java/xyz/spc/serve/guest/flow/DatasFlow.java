package xyz.spc.serve.guest.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Guest.datas.CollectDO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;
import xyz.spc.gate.vo.Data.files.FileShowVO;
import xyz.spc.gate.vo.Guest.datas.CollectCountVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.infra.feign.Cluster.InteractsClient;
import xyz.spc.infra.feign.Data.FilesClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.guest.func.datas.CollectFunc;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasFlow {

    //Feign
    private final InteractsClient interactsClient;
    private final FilesClient filesClient;
    private final ClustersClient clustersClient;

    //Func
    private final CollectFunc collectFunc;


    //! Client

    public CollectCountVO getUserDataOfAllCollect() {
        return null;
    }


    public PageResponse<PostShowVO> getUserDataOfPost(PageRequest pageRequest) {

        //? note 之后也是像这样, 哪个区域的业务, 就主要由哪个区域的Flow来进行编排处理, 哪怕需要涉及到多个调用 (因为你需要去处理外挂的逻辑, 就很Dirty. Feign的调用在这里性能忽略掉.

        // 这边的链路是这样的:
        //
        //1. 前端用户需要先查自己的 收藏表, 得到具体收藏了哪些东西, 把对应的东西取出来, 作为去获取的清单.
        //2. 用清单去对应的Feign进行处理, 具体就是用 对应物品的 id + 用户 id 进行where查询, 之后把这个查出来的所有数据返回回去
        //3. 前端展示数据.

        Long userId = Objects.requireNonNull(UserContext.getUI());


        // 获取这个用户收藏的动态列表
        List<CollectDO> collectList = collectFunc.getUserCollectListOfPost(userId);


        // 收集 所有 需要去查询的 Post id
        List<Long> postIds = collectList.stream()
                .map(CollectDO::getTargetId)
                .toList();


        // 提前进行逻辑分页操作: 将 分页查询 降级为 批量查询, 对方服务只需要查询提供的 ids
        int currentPage = pageRequest.getCurrent();
        int pageSize = pageRequest.getSize();
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, postIds.size());

        // 获取当前页的 Post id

        // 避免 越界
        if (fromIndex >= postIds.size()) {
            return new PageResponse<>(currentPage, pageSize, postIds.size(), List.of());
        }

        List<Long> pagedPostIds = postIds.subList(fromIndex, toIndex);

        // 批量查询动态对象
        List<PostShowVO> postShowVOS = interactsClient.getPostByIdBatch(pagedPostIds);


        // 构建分页响应
        PageResponse<PostShowVO> res = new PageResponse<>(
                pageRequest.getCurrent(),
                pageRequest.getSize(),
                postIds.size(),
                postShowVOS
        );

        return res;

    }


    public PageResponse<FileShowVO> getUserDataOfFile(PageRequest pageRequest) {

        Long userId = Objects.requireNonNull(UserContext.getUI());

        //? 这边的思路和上面一样
        //1. 获取需要的文件对象id清单 (手动分页ids)
        //2. 批量查询文件对象, 只需要补充其中需要的字段
        //3. 返回给前端, 进行展示

        // 获取这个用户收藏的文件列表
        List<CollectDO> collectList = collectFunc.getUserCollectListOfFile(userId);

        // 收集 所有 需要去查询的 File id
        List<Long> fileIds = collectList.stream()
                .map(CollectDO::getTargetId)
                .toList();

        // 提前进行逻辑分页操作: 将 分页查询 降级为 批量查询, 对方服务只需要查询提供的 ids
        int currentPage = pageRequest.getCurrent();
        int pageSize = pageRequest.getSize();
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, fileIds.size());

        // 避免 越界
        if (fromIndex >= fileIds.size()) {
            return new PageResponse<>(currentPage, pageSize, fileIds.size(), List.of());
        }

        // 获取当前页的 File id
        List<Long> pagedFileIds = fileIds.subList(fromIndex, toIndex);

        // 批量查询文件对象
        List<FileShowVO> fileShowVOS = filesClient.getFileByIdBatch(pagedFileIds);

        // 构建分页响应
        PageResponse<FileShowVO> res = new PageResponse<>(
                pageRequest.getCurrent(),
                pageRequest.getSize(),
                fileIds.size(),
                fileShowVOS
        );


        return res;
    }

    public PageResponse<ClusterVO> getUserDataOfCluster(PageRequest pageRequest) {

        Long userId = Objects.requireNonNull(UserContext.getUI());

        //? 这边的思路和上面一样

        // 获取这个用户收藏的群组列表
        List<CollectDO> collectList = collectFunc.getUserCollectListOfCluster(userId);

        // 收集所有需要查询的 Cluster id
        List<Long> clusterIds = collectList.stream()
                .map(CollectDO::getTargetId)
                .toList();

        // 提前进行逻辑分页操作: 将分页查询降级为批量查询，对方服务只需要查询提供的 ids
        int currentPage = pageRequest.getCurrent();
        int pageSize = pageRequest.getSize();
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, clusterIds.size());

        // 避免越界
        if (fromIndex >= clusterIds.size()) {
            return new PageResponse<>(currentPage, pageSize, clusterIds.size(), List.of());
        }

        // 获取当前页的 Cluster id
        List<Long> pagedClusterIds = clusterIds.subList(fromIndex, toIndex);

        // 批量查询群组对象
        List<ClusterVO> clusterVOS = clustersClient.getClusterByIdBatch(pagedClusterIds);

        // 构建分页响应
        PageResponse<ClusterVO> res = new PageResponse<>(
                pageRequest.getCurrent(),
                pageRequest.getSize(),
                clusterIds.size(),
                clusterVOS
        );

        return res;

    }

}
