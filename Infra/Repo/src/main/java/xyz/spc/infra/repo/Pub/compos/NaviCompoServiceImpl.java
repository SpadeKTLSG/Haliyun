package xyz.spc.infra.repo.Pub.compos;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Pub.compos.NaviCompoDO;
import xyz.spc.infra.mapper.Pub.compos.NaviCompoMapper;

@Service
@RequiredArgsConstructor
public class NaviCompoServiceImpl extends ServiceImpl<NaviCompoMapper, NaviCompoDO> implements NaviCompoService {
}
