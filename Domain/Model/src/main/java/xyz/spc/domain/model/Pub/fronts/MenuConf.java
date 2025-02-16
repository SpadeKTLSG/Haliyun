package xyz.spc.domain.model.Pub.fronts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 前端菜单配置
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuConf extends BaseModel {


    private Long id;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * -> User.account
     */
    private String userAccount;

    /**
     * -> Menu    菜单
     */
    private Long menuId;

    /**
     * 名称-> Menu.name
     */
    private String menuName;

    /**
     * 状态    0启用, 1停用
     */
    private Integer status;
    public static final int STATUS_ENABLE = 0;
    public static final int STATUS_DISABLE = 1;

    /**
     * 配置值    0 正常 1 隐藏
     */
    private Integer value;
    public static final int VALUE_NORMAL = 0;
    public static final int VALUE_HIDE = 1;


}
