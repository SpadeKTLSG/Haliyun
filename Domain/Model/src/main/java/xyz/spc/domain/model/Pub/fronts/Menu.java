package xyz.spc.domain.model.Pub.fronts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 前端菜单
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseModel {

    /**
     * int unsigned (适配, 必须自增长, 不可新增) NOT NULL AUTO_INCREMENT
     */
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 类型 0：目录 1：菜单 2：按钮
     */
    private Integer type;
    public static final int TYPE_CATALOG = 0;
    public static final int TYPE_MENU = 1;
    public static final int TYPE_BUTTON = 2;

    /**
     * 是否仅管理可见 (0不 1需要管理员权限)
     */
    private Integer admin;
    public static final int ADMIN_NO = 0;
    public static final int ADMIN_YES = 1;

    /**
     * 名称 (唯一)
     */
    private String name;

    /**
     * 菜单动态路由 Plan (页面路径) => 当前取消
     * <p>示例: 系统核心页面 '菜单管理'index的URL: 'sys/menu', 位于前端的modules/ **sys/menu** /index.vue.
     * <p>则自己的  /modules/Guest/users/index.vue 用户微服务的users逻辑区块的index(默认微服务下面的业务包作为一个页面)
     * <p>需要填写的URL即为: Guest/users
     */
    private String url;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 备注
     */
    private String remark;
}
