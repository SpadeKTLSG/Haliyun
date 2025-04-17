package xyz.spc.gate.vo.Guest.users;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends BaseVO {


    private String id;

    private Integer admin;

    private Integer status;

    private Integer loginType;

    private String account;

    private String password;


}
