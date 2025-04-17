package xyz.spc.gate.vo.Cluster.managers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileLockVO extends BaseVO {

    private String id;

    private String clusterId;

    private String ownerId;

    private String ownerAccount;

    private Integer currencyType;

    private Integer lockType;

    private Integer levelLimit;

    private Integer cost;
}
