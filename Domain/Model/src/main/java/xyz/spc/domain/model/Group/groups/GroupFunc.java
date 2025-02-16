package xyz.spc.domain.model.Group.groups;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 群组功能
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupFunc extends BaseModel {


    private Long id;

    /**
     * 通知 -> Notice
     */
    private Long noticeId;

    /**
     * 货币类型 -> Currency 流通的货币类型
     */
    private Long currencyId;

    /**
     * 允许邀请 0 允许 1 不允许
     */
    private Integer allowInvite;
    public static final int ALLOW_INVITE_ALLOW = 0;
    public static final int ALLOW_INVITE_DENY = 1;

    /**
     * 货币储备
     */
    private Long currencyStock;

    /**
     * 硬币储备
     */
    private Long coinStock;

    /**
     * 群组的评论 JSON
     */
    private String remarks;
}
