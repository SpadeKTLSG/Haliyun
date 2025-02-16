package xyz.spc.infra.special.Group.functions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.functions.NoticeMapper;
import xyz.spc.infra.repo.Group.functions.NoticeService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class NoticeRepo {

    public final NoticeService noticeService;
    public final NoticeMapper noticeMapper;
}
