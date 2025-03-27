package xyz.spc.gate.dto.Cluster.clusters;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterDetailDTO extends BaseDTO {


    private Long id;

    private String shareLink;

    private String album;

    private Long usedSpace;

    private Long totalSpace;
}
