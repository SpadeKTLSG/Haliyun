package xyz.spc.infra.repo.Pub.fronts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Pub.fronts.MenuDO;
import xyz.spc.infra.mapper.Pub.fronts.MenuMapper;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {
}
