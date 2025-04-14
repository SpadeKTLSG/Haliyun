package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Data.attributes.FileTagDO;
import xyz.spc.gate.vo.Data.attributes.FileTagVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.data.func.attributes.FileTagFunc;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttributesFlow {


    //Feign
    private final ClustersClient clustersClient;

    //Func
    private final FileTagFunc fileTagFunc;


    /**
     * 获取自己创建的所有标签对象list
     */
    public List<FileTagVO> getAllTags() {

        List<FileTagDO> tmp = fileTagFunc.getAllTags();


        // 2 某人很熟悉的处理方法
        List<FileTagVO> res = new ArrayList<>();
        tmp.forEach(tag -> {
                    FileTagVO fileTagVO = FileTagVO.builder()
                            .id(tag.getId())
                            .status(tag.getStatus())
                            .name(tag.getName())
                            .build();
                    res.add(fileTagVO);
                }
        );

    }
}
