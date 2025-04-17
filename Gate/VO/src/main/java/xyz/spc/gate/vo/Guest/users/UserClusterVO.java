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

    private String id;

    private String userId;

    private String clusterId;

    private String collect;

    private Integer sort;
}
