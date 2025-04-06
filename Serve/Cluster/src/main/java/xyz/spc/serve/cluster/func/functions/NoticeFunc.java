package xyz.spc.serve.cluster.func.functions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.functions.NoticeDO;
import xyz.spc.infra.special.Cluster.functions.NoticeRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeFunc {

    /**
     * Repo
     */
    private final NoticeRepo noticeRepo;

    /**
     * 根据id获取公告
     */
    public NoticeDO getNoticeById(Long noticeId) {
        return noticeRepo.noticeService.getById(noticeId);
    }
}
