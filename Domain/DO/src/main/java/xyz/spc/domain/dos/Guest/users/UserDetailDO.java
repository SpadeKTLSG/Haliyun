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


    private Long id;

    private Integer gender;

    private String phone;

    private String email;

    private String avatar;

    private String area;

    private String nickname;

    private String introduce;


}
