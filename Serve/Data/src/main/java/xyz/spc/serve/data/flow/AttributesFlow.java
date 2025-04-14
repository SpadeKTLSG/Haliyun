package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.domain.dos.Data.attributes.FileTagDO;
import xyz.spc.gate.vo.Data.attributes.FileTagVO;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
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

        // 1 鉴权: 操作者必须是对应文件所在群的群主
    }


    /**
     * 删除对应文件的标签
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteTag(Long fileId, String tagName) {
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
