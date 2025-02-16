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
@TableName("user")
public class UserDO extends BaseDO {


    private Long id;

    private Integer admin;

    private Integer status;

    private Integer loginType;

    private String account;

    private String password;


}
