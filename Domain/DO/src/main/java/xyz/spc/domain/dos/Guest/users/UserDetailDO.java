package xyz.spc.domain.dos.Guest.users;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_detail")
public class UserDetailDO extends BaseDO {

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
