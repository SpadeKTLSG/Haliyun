package xyz.spc.domain.model.Guest.users;


import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 用户详情
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail extends BaseModel {

    /**
     * User主键
     */
    private Long id;

    /**
     * 性别 (女0 男1 不明2)
     */
    private Integer gender;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像图片
     */
    private String avatar;

    /**
     * 地区
     */
    private String area;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 个人介绍
     */
    private String introduce;
}
