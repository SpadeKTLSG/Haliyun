package xyz.spc.serve.pub.func.systems;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Pub.fronts.MenuDO;
import xyz.spc.gate.vo.Pub.fronts.MenuVO;
import xyz.spc.infra.special.Pub.systems.SystemsRepo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuFunc {

    /**
     * Repo
     */
    private final SystemsRepo systemsRepo;

    public List<MenuVO> listNav() {

        List<MenuDO> res = this.list(new LambdaQueryWrapper<MenuDO>()
                .ne(MenuDO::getType, 2)
                .orderByAsc(MenuDO::getOrderNum)
        );

        Map<Long, List<MenuDO>> MenuDOMap = res.stream()
                .sorted(Comparator.comparing(MenuDO::getOrderNum))
                .collect(Collectors.groupingBy(MenuDO::getParentId));

        List<MenuDO> rootMenu = menuMap.get(0L);
        if (CollectionUtil.isEmpty(rootMenu)) {
            return Collections.emptyList();
        }

        //补充T字段
        List<MenuVO> rootMenuVO = rootMenu.stream().map(menu -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu, menuVO);
            menuVO.setList(menuMap.get(menu.getMenuId()));
            return menuVO;
        }).collect(Collectors.toList());

        rootMenuVO.forEach(menuVO -> {
            List<Menu> childMenus = menuMap.get(menuVO.getMenuId());
            if (childMenus != null) {
                List<MenuVO> childMenuVOs = childMenus.stream().map(childMenu -> {
                    MenuVO childMenuVO = new MenuVO();
                    BeanUtils.copyProperties(childMenu, childMenuVO);
                    childMenuVO.setParentName("null"); // 手动设置 parentName = null 字段适配前端
                    return childMenuVO;
                }).collect(Collectors.toList());
                menuVO.setList(childMenuVOs);
            }
        });

        return rootMenuVO;
    }
}
