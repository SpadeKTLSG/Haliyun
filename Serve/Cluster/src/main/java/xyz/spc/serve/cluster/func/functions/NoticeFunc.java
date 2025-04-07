package xyz.spc.serve.cluster.func.functions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Cluster.functions.NoticeDO;
import xyz.spc.gate.dto.Cluster.functions.NoticeDTO;
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

    /**
     * 通过 DO 对象更新公告
     */
    public void updateNoticeByDO(NoticeDO tmp) {
        noticeRepo.noticeService.updateById(tmp);
    }

    /**
     * 添加公告
     */
    public Long addNotice(NoticeDTO noticeDTO) {

        Long id = SnowflakeIdUtil.nextId();

        NoticeDO tmp = NoticeDO.builder()
                .id(id)
                .name(noticeDTO.getName())
                .content(noticeDTO.getContent())
                .build();

        noticeRepo.noticeService.save(tmp);

        // note: 由于希望群公告是唯一的, 因此需要把之前的公告给删掉 或者 就限制如果群有公告了就不插入新的. 这个前端直接屏蔽掉了

        return id;
    }

    /**
     * id删除公告
     */
    public void delNoticeById(Long noticeId) {
        noticeRepo.noticeService.removeById(noticeId);
    }
}
