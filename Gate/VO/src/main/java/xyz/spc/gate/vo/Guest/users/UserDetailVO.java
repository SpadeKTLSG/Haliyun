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
public class UserDetailVO extends BaseVO {

    private String id;

    private Integer gender;

    private String phone;

    private String email;

    private String avatar;

    private String area;

    private String nickname;

    private String introduce;


}
