package xyz.spc.domain.model.Guest.levels;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 等级
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Level extends BaseModel {

    private Long id;

    /**
     * 等级层级
     */
    private Integer floor;

    /**
     * 等级名称
     */
    private String name;

    /**
     * 折扣优惠小数倍率 (0.8 = 8折)
     */
    private Float cut;

    /**
     * 到下一级需要成长值
     */
    private Long grow;

    /**
     * 等级描述
     */
    private String dscr;

    /**
     * 备注
     */
    private String remark;

}
