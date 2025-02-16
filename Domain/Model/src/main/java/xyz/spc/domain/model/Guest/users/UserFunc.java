package xyz.spc.domain.model.Guest.users;


import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 用户功能
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserFunc extends BaseModel {

    /**
     * -> User主键
     */
    private Long id;

    /**
     * 等级
     */
    private Long levelId;

    /**
     * 是否VIP 0否 1是
     */
    private Integer vip;
    public static final int VIP_NO = 0;
    public static final int VIP_YES = 1;

    /**
     * 当前创建群组数
     */
    private Integer createGroupCount;

    /**
     * 最大创建群组数
     */
    private Integer createGroupMax;

    /**
     * 当前加入群组数
     */
    private Integer joinGroupCount;

    /**
     * 最大加入群组数
     */
    private Integer joinGroupMax;

    /**
     * 硬币数 (指平台硬币)
     */
    private Long coin;

    /**
     * (换算的)能量币数 (指群内硬币的估价总额)
     */
    private Long energyCoin;

    /**
     * 注册邀请码 (唯一)
     */
    private String registerCode;

    //? 转换器

    //! 基础信息
}
