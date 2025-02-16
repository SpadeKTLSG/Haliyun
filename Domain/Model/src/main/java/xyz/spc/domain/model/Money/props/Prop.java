package xyz.spc.domain.model.Money.props;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 道具
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Prop extends BaseModel {

    private Long id;

    /**
     * 类型 0 (平台)硬币 1 (群组)能量币
     */
    private Integer type;
    public static final int TYPE_COIN = 0;
    public static final int TYPE_ENERGY = 1;

    /**
     * 名称 (唯一)
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 硬币/能量币的价格
     */
    private Integer price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 图片
     */
    private String pic;

    /**
     * 属性(JSON)
     */
    private String attribute;
}
