package xyz.spc.domain.model.Cluster.clusters;

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
public class ClusterFunc extends BaseModel {


    private Long id;

    /**
     * 通知 -> Notice
     */
    private Long noticeId;
    public static final Long DEFAULT_NOTICE_ID = 1907762497877065728L;

    /**
     * 货币类型 -> Currency 流通的货币类型
     */
    private Long currencyId;
    public static final Long DEFAULT_CURRENCY_ID = 1907762497822539776L;
    //默认的货币类型是硬币, 其指向的群组id 字段 为1L, 代表全部.


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
    public static final Long DEFAULT_CURRENCY_STOCK = 100L;

    /**
     * 硬币储备
     */
    private Long coinStock;
    public static final Long DEFAULT_COIN_STOCK = 1000L;

    /**
     * 群组的评论 JSON
     */
    private String remark;
    public static final String DEFAULT_REMARK = "群主很懒, 什么都没有写~";
}
