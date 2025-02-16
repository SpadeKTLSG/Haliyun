package xyz.spc.domain.model.Money.events;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;


/**
 * 活动
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Outlet extends BaseModel {

    private Long id;

    /**
     * 状态 0 未开始 1 进行中 2 已结束
     */
    private Integer status;
    public static final int STATUS_NOT_START = 0;
    public static final int STATUS_ONGOING = 1;
    public static final int STATUS_FINISHED = 2;

    /**
     * 名称 (唯一)
     */
    private String name;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 备注
     */
    private String remark;

    /**
     * 活动类型  0 参与即领奖
     */
    private Integer type;
    public static final int TYPE_JOIN_GET = 0;

    /**
     * 奖励经验数值
     */
    private Long bonusExp;

    /**
     * 奖励硬币数值
     */
    private Long bonusCoin;

}
