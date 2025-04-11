package xyz.spc.serve.cluster.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.dos.Cluster.functions.NoticeDO;
import xyz.spc.gate.dto.Cluster.functions.NoticeDTO;
import xyz.spc.gate.vo.Cluster.functions.NoticeVO;
import xyz.spc.serve.cluster.func.clusters.ClustersFunc;
import xyz.spc.serve.cluster.func.functions.NoticeFunc;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FunctionsFlow {


    //Feign


    //Func
    private final NoticeFunc noticeFunc;
    private final ClustersFunc clustersFunc;

    /**
     * 根据 群组 clusterId 获取这一群的公告
     */
    public NoticeVO getNotice(Long clusterId) {

        //1. 通过 ClusterFunc 得到存储的 noticeId
        Long noticeId = clustersFunc.getNoticeIdByClusterId(clusterId);

        //2. 通过 NoticeFunc 得到公告
        NoticeDO tmp = Optional.ofNullable(noticeFunc.getNoticeById(noticeId)).orElseThrow(
                () -> new ClientException("公告已经不存在了, 无法查看!!!")
        );


        //3. 记录阅读数, 并保存
        tmp.setReadCount(tmp.getReadCount() + 1);
        noticeFunc.updateNoticeByDO(tmp);

        //4. 转换成 NoticeVO
        NoticeVO res = NoticeVO.builder()
                .id(tmp.getId())
                .name(tmp.getName())
                .content(tmp.getContent())
                .readCount(tmp.getReadCount())
                .build();

        return res;
    }

    /**
     * 添加公告
     */
    public void addNotice(Long clusterId, NoticeDTO noticeDTO) {

        //1. Notice 落库
        Long noticeId = noticeFunc.addNotice(noticeDTO);

        //2. 把 cluster 的 noticeId 更新为这个公告的 id
        clustersFunc.updateNoticeId(clusterId, noticeId);
    }

    /**
     * 删除公告
     */
    public void deleteNotice(Long clusterId, Long noticeId) {

        //1. 删除公告
        noticeFunc.delNoticeById(noticeId);

        //2. 更新 cluster 的 noticeId 为 null (复用更新接口)
        clustersFunc.updateNoticeId(clusterId, null);
    }

    /**
     * 更新公告
     */
    public void updateNotice(NoticeDTO noticeDTO) {

        //1. 更新公告内容, 复用

        //1.1 获取之前的公告信息
        NoticeDO tmp = Optional.ofNullable(noticeFunc.getNoticeById(noticeDTO.getId())).orElseThrow(
                () -> new ClientException("公告已经不存在了, 无法更新!!!")
        );

        //1.2 更新公告内容, 阅读数不能动
        tmp.setName(noticeDTO.getName());
        tmp.setContent(noticeDTO.getContent());

        //1.3 落库
        noticeFunc.updateNoticeByDO(tmp);

        //2. 只是更新不需要调整

    }
}
