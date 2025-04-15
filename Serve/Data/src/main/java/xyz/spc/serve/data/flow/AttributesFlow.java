package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.dos.Data.attributes.FileTagDO;
import xyz.spc.domain.model.Data.attributes.FileTag;
import xyz.spc.gate.vo.Data.attributes.FileTagVO;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.data.func.attributes.FileTagFunc;
import xyz.spc.serve.data.func.files.FilesFunc;

import java.util.List;

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
        if (tmp == null) {
            return null;
        }

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

        // 1 鉴权

        // 1.1 操作者必须是对应文件所在群的群主
        if (!filesFlow.amICreatorOfFileCluster(fileId)) {
            throw new ClientException(ClientError.USER_AUTH_ERROR);
        }

        // 1.2 判断这个文件是不是已经有标签了
        if (this.getFileTag(fileId) != null) {
            throw new ClientException("该文件已经有标签了, 请删除后再添加");
        }


        // 2 创建 Tag 对象
        FileTagDO toInsert = FileTagDO.builder()
                .name(tagName)
                .status(FileTag.STATUS_NORMAL)
                .build();

        Long tagID = fileTagFunc.createTag(toInsert);


        // 3 新增关联关系
        // 更新对应文件对象
        filesFunc.updateFileFunc(tagID, fileId);

    }


    /**
     * 删除对应文件的标签
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteTag(Long fileId) {


        // 1 鉴权

        // 1.1 操作者必须是对应文件所在群的群主
        if (!filesFlow.amICreatorOfFileCluster(fileId)) {
            throw new ClientException(ClientError.USER_AUTH_ERROR);
        }

        // 2 找到对应文件的标签
        FileTagVO tmp = this.getFileTag(fileId);
        Long tagId = tmp.getId();

        // 3 直接删除 (对应文件的关联关系不处理, 直接降级)
        fileTagFunc.deleteTag(tagId);
    }


    /**
     * 更新对应文件的标签
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(Long fileId, String tagName) {

        // 1 直接调用先删除后添加
        this.deleteTag(fileId);
        this.addTag(fileId, tagName);
    }

    /**
     * 通过标签来查询该标签下的我的所有文件, 做 FileVO 的列表展示即可
     */
    public List<FileTagVO> getMyFilesByTag(Long tagId) {

    }

    /**
     * 更新暂停标签
     */
    public void pauseTag(Long tagId) {

        // 这三个简单接口的鉴权通过前端界面来做, 用户默认只能修改自己群里的标签, 这个的鉴权外包出去作为调用

        // 1 更新字段
        fileTagFunc.updateTag4Status(tagId, FileTag.STATUS_PAUSE);
    }

    /**
     * 更新冻结标签
     */
    public void freezeTag(Long tagId) {

        // 这三个简单接口的鉴权通过前端界面来做, 用户默认只能修改自己群里的标签, 这个的鉴权外包出去作为调用

        // 1 更新字段
        fileTagFunc.updateTag4Status(tagId, FileTag.STATUS_FREEZE);
    }

    /**
     * 更新正常标签
     */
    public void normalTag(Long tagId) {

        // 这三个简单接口的鉴权通过前端界面来做, 用户默认只能修改自己群里的标签, 这个的鉴权外包出去作为调用

        // 1 更新字段
        fileTagFunc.updateTag4Status(tagId, FileTag.STATUS_NORMAL);
    }
}
