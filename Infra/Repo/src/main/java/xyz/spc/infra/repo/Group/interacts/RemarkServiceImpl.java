package xyz.spc.infra.repo.Group.interacts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.interacts.RemarkDO;
import xyz.spc.infra.mapper.Group.interacts.RemarkMapper;

@Service
@RequiredArgsConstructor
public class RemarkServiceImpl extends ServiceImpl<RemarkMapper, RemarkDO> implements RemarkService {
}
