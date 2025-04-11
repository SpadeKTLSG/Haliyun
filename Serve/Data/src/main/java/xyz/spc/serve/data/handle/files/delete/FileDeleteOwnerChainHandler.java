package xyz.spc.serve.data.handle.files.delete;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.model.Data.files.File;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.data.func.files.FilesFunc;

import java.util.Objects;

/**
 * 文件删除校验用户责任链处理器
 */
@Component
@RequiredArgsConstructor
public class FileDeleteOwnerChainHandler implements FileDeleteChainFilter<File, FileGreatVO> {

    private final FilesFunc filesFunc;
    private final ClustersClient clustersClient;


    @Override
    public void handler(File file, FileGreatVO fileGreatVO) {

        // 需要判断用户是否有权限删除文件 : 默认仅为 上传者 / 用户 才可以
        boolean auth = false;

        Long nowUserId = UserContext.getUI();

        // 查出文件的上传者, 和当前用户进行对比
        auth = Objects.equals(fileGreatVO.getUserId(), nowUserId);


        if(!auth){
            // 查出文件所在群的群主 (管理), 和当前用户进行对比
            Long cluster_id = fileGreatVO.getClusterId();
            auth = clustersClient.checkClusterCreatorEqual(cluster_id, nowUserId);
        }


        if (!auth) {
            throw new ClientException(ClientError.USER_AUTH_ERROR);
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
