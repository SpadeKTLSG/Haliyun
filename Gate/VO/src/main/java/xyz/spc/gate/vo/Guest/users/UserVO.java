package xyz.spc.gate.vo.Guest.users;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserVO extends BaseVO {


    private Long id;

    private Integer admin;

    private Integer status;

    private Integer loginType;

    private String account;

    private String password;


}
