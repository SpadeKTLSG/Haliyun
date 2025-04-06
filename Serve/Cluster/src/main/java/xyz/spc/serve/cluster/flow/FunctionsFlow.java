package xyz.spc.serve.cluster.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.functions.NoticeDO;
import xyz.spc.gate.vo.Cluster.functions.NoticeVO;
import xyz.spc.serve.cluster.func.clusters.ClustersFunc;
import xyz.spc.serve.cluster.func.functions.NoticeFunc;

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
        NoticeDO tmp = noticeFunc.getNoticeById(noticeId);

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

}
