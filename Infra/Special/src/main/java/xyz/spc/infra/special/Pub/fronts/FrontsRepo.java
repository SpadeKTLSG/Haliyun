package xyz.spc.infra.special.Pub.fronts;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Pub.fronts.MenuConfMapper;
import xyz.spc.infra.mapper.Pub.fronts.MenuMapper;
import xyz.spc.infra.repo.Pub.fronts.MenuConfService;
import xyz.spc.infra.repo.Pub.fronts.MenuService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class FrontsRepo {

    public final MenuService menuService;
    public final MenuMapper menuMapper;
    public final MenuConfService menuConfService;
    public final MenuConfMapper menuConfMapper;

}
