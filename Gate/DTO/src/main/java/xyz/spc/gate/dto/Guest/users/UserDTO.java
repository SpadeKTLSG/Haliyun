package xyz.spc.gate.dto.Guest.users;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {

    private Long id;

    private Long groupId;

    private Integer admin;

    private Integer status;

    private Integer loginType;

    private String account;

    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;
}
