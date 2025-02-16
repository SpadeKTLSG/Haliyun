package xyz.spc.serve.pub.flow.systems;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.gate.vo.Pub.fronts.MenuVO;
import xyz.spc.serve.pub.func.systems.MenuFunc;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemsFlow {

    // Func
    private final MenuFunc menuFunc;


    /**
     * 获取菜单
     */
    public List<MenuVO> listNav() {
        return menuFunc.listNav();
    }
}
