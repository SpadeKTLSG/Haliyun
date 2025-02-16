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
public class UserDetailDTO extends BaseDTO {

    private Long id;

    private Integer gender;

    private String phone;

    private String email;

    private String avatar;

    private String area;

    private String nickname;

    private String introduce;


}
