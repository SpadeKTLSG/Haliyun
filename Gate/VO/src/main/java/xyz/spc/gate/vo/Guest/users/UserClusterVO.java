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
public class UserClusterVO extends BaseVO {

    private Long id;

    private Long userId;

    private Long groupId;

    private String collect;

    private Integer sort;
}
