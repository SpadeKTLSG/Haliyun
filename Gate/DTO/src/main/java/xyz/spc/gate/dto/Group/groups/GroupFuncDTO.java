package xyz.spc.gate.dto.Cluster.groups;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterFuncDTO extends BaseDTO {

    private Long id;

    private Long noticeId;

    private Long currencyId;

    private Integer allowInvite;

    private Long currencyStock;

    private Long coinStock;

    private String remarks;
}
