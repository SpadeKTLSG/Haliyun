package xyz.spc.infra.repo.Pub.fronts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Pub.fronts.MenuConfDO;
import xyz.spc.infra.mapper.Pub.fronts.MenuConfMapper;

@Service
@RequiredArgsConstructor
public class MenuConfServiceImpl extends ServiceImpl<MenuConfMapper, MenuConfDO> implements MenuConfService {
}
