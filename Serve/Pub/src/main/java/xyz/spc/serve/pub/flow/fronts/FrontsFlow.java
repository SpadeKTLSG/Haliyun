package xyz.spc.serve.pub.flow.fronts;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.gate.vo.Pub.fronts.MenuVO;
import xyz.spc.serve.pub.func.fronts.MenuFunc;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FrontsFlow {

    // Func
    private final MenuFunc menuFunc;


    /**
     * 获取菜单
     */
    public List<MenuVO> listNav() {
        return menuFunc.listNav();
    }
}
