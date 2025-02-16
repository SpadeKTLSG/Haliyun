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
public class UserFuncVO extends BaseVO {

    private Long id;

    private Long levelId;

    private Integer vip;

    private Integer createGroupCount;

    private Integer createGroupMax;

    private Integer joinGroupCount;

    private Integer joinGroupMax;

    private Long coin;

    private Long energyCoin;

    private String registerCode;
}
