package xyz.spc.infra.repo.Cluster.functions;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.functions.NoticeDO;
import xyz.spc.infra.mapper.Cluster.functions.NoticeMapper;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeDO> implements NoticeService {
}
