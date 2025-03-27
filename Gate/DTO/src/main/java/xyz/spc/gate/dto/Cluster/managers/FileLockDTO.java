package xyz.spc.gate.dto.Cluster.managers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileLockDTO extends BaseDTO {
    private Long id;

    private Long clusterId;

    private Long ownerId;

    private String ownerAccount;

    private Integer currencyType;

    private Integer lockType;

    private Integer levelLimit;

    private Integer cost;
}
