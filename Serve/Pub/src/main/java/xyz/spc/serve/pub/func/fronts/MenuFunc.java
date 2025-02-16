package xyz.spc.serve.pub.func.fronts;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Pub.fronts.MenuDO;
import xyz.spc.gate.vo.Pub.fronts.MenuVO;
import xyz.spc.infra.special.Pub.fronts.FrontsRepo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuFunc {

    /**
     * Repo
     */
    private final FrontsRepo frontsRepo;

    public List<MenuVO> listNav() {

        // 全部查出来 页面+菜单
        List<MenuDO> tmp = frontsRepo.menuService.list(new LambdaQueryWrapper<MenuDO>()
                .ne(MenuDO::getType, 2)
                .orderByAsc(MenuDO::getScore)
        );

        // 按照 parentId 分组
        Map<Long, List<MenuDO>> resMap = tmp.stream()
                .sorted(Comparator.comparing(MenuDO::getScore))
                .collect(Collectors.groupingBy(MenuDO::getParentId));

        // 获取根节点
        List<MenuDO> rootMenu = resMap.get(0L);

        Objects.requireNonNull(rootMenu, "菜单跑丢啦");

        //补充T字段
        List<MenuVO> rootMenuVO = rootMenu.stream().map(menu -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu, menuVO);
            menuVO.setList(resMap.get(menu.getId()));
            return menuVO;
        }).collect(Collectors.toList());

        // 递归设置子菜单
        rootMenuVO.forEach(menuVO -> {
            List<MenuDO> childMenus = resMap.get(menuVO.getId());

            if (childMenus != null) {
                List<MenuVO> childMenuVOs = childMenus.stream().map(childMenu -> {
                    MenuVO childMenuVO = new MenuVO();
                    BeanUtils.copyProperties(childMenu, childMenuVO);
                    childMenuVO.setParentName("null"); // 适配前端
                    return childMenuVO;
                }).collect(Collectors.toList());
                menuVO.setList(childMenuVOs);
            }
        });

        return rootMenuVO;
    }
}
