package xyz.spc.domain.model.Cluster.managers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 文件锁
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileLock extends BaseModel {


    private Long id;

    /**
     * -> Cluster主键
     */
    private Long groupId;

    /**
     * -> User主键 拥有者
     */
    private Long ownerId;

    /**
     * -> User.account 拥有者账号
     */
    private String ownerAccount;

    /**
     * 货币类型 0 平台货币 1 群组货币
     */
    private Integer currencyType;
    public static final int CURRENCY_TYPE_PLATFORM = 0;
    public static final int CURRENCY_TYPE_GROUP = 1;

    /**
     * 锁类型 0 货币锁 1 用户锁 2 本人锁
     */
    private Integer lockType;
    public static final int LOCK_TYPE_CURRENCY = 0;
    public static final int LOCK_TYPE_USER = 1;
    public static final int LOCK_TYPE_SELF = 2;

    /**
     * 等级限制 -> Level.floor
     */
    private Integer levelLimit;

    /**
     * 花费货币个数
     */
    private Integer cost;


}
