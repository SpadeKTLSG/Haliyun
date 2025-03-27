package xyz.spc.infra.special.Cluster.functions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Cluster.functions.NoticeMapper;
import xyz.spc.infra.repo.Cluster.functions.NoticeService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class NoticeRepo {

    public final NoticeService noticeService;
    public final NoticeMapper noticeMapper;
}
