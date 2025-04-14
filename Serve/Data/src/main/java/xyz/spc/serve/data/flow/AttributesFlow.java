package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.dos.Data.attributes.FileTagDO;
import xyz.spc.domain.dos.Data.files.FileFuncDO;
import xyz.spc.domain.model.Data.attributes.FileTag;
import xyz.spc.gate.vo.Data.attributes.FileTagVO;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.data.func.attributes.FileTagFunc;
import xyz.spc.serve.data.func.files.FilesFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttributesFlow {


    // Feign
    private final ClustersClient clustersClient;

    // Flow
    private final FilesFlow filesFlow;


    // Func
    private final FileTagFunc fileTagFunc;
    private final FilesFunc filesFunc;


    /**
     * 查询对应文件id的指向的标签
     */
    public FileTagVO getFileTag(Long fileId) {

        // 1 查对应文件的Func
        FileGreatVO oneFileAllInfo = filesFlow.getOneFileAllInfo(fileId);

        // 2 通过Func, 找到 tag -> Tag
        Long tagId = oneFileAllInfo.getTag();

        // 3 id查
        FileTagDO tmp = fileTagFunc.getById(tagId);

        // 4 转化为VO
        FileTagVO res = FileTagVO.builder()
                .id(tmp.getId())
                .name(tmp.getName())
                .status(tmp.getStatus())
                .build();

        return res;
    }

    /**
     * 创建对应文件的标签
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void addTag(Long fileId, String tagName) {

        // 1 鉴权: 操作者必须是对应文件所在群的群主; 这个文件是不是已经有标签了
        // 1.1 查对应文件的信息
        FileGreatVO oneFileAllInfo = filesFlow.getOneFileAllInfo(fileId);

        // 1.2 找到对应群组id
        Long clusterId = oneFileAllInfo.getClusterId();

        // 1.3 判断我是不是这个群组的群主
        Long myUserId = UserContext.getUI();
        boolean amIMaster = clustersClient.checkClusterCreatorEqual(clusterId, myUserId);

        // 1.4 如果不是, 抛出异常
        if (!amIMaster) {
            throw new ClientException(ClientError.USER_AUTH_ERROR);
        }

        // 1.5 判断这个文件是不是已经有标签了
        if (oneFileAllInfo.getTag() != null) {
            throw new ClientException("该文件已经有标签了, 请删除后再添加");
        }


        // 2 创建 Tag 对象
        FileTagDO toInsert = FileTagDO.builder()
                .name(tagName)
                .status(FileTag.STATUS_NORMAL)
                .build();

        Long tagID = fileTagFunc.createTag(toInsert);


        // 3 新增关联关系

        // 3.1 创建更新的 FileFunc 对象
        FileFuncDO fileFuncDO = FileFuncDO.builder()
                .id(oneFileAllInfo.getId())
                .tag(tagID)
                .fileLock(oneFileAllInfo.getFileLock())
                .status(oneFileAllInfo.getStatus())
                .validDateType(oneFileAllInfo.getValidDateType())
                .validDate(oneFileAllInfo.getValidDate())
                .build();

        // 3.2 更新对应文件对象
        filesFunc.updateFileFunc(fileFuncDO);
    }


    /**
     * 删除对应文件的标签
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteTag(Long fileId, String tagName) {


        // 1 鉴权: 操作者必须是对应文件所在群的群主; 这个文件是不是已经有标签了
        // 1.1 查对应文件的信息
        FileGreatVO oneFileAllInfo = filesFlow.getOneFileAllInfo(fileId);

        // 1.2 找到对应群组id
        Long clusterId = oneFileAllInfo.getClusterId();

        // 1.3 判断我是不是这个群组的群主
        Long myUserId = UserContext.getUI();
        boolean amIMaster = clustersClient.checkClusterCreatorEqual(clusterId, myUserId);

        // 1.4 如果不是, 抛出异常
        if (!amIMaster) {
            throw new ClientException(ClientError.USER_AUTH_ERROR);
        }
    }


    /**
     * 更新对应文件的标签
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(Long fileId, String tagName) {

        // 1 直接调用先删除后添加
        this.deleteTag(fileId, tagName);
        this.addTag(fileId, tagName);
    }
}
