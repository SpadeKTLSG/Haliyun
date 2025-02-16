package xyz.spc.domain.model.Money.standards;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 货币
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends BaseModel {

    private Long id;

    /**
     * 对应流通群组 -> Group
     */
    private Long groupId;


    /**
     * 名称 (唯一)
     */
    private String name;

    /**
     * 和能量币的换算倍率(1个货币等于多少能量币)
     */
    private Float exchangeRate;

    /**
     * 图片
     */
    private String pic;

}
