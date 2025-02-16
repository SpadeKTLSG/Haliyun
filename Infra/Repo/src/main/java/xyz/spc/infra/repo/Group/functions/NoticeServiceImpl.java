package xyz.spc.infra.repo.Group.functions;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.functions.NoticeDO;
import xyz.spc.infra.mapper.Group.functions.NoticeMapper;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeDO> implements NoticeService {
}
