package xyz.spc.gate.vo.Cluster.clusters;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterDetailVO extends BaseVO {


    private Long id;

    private String shareLink;

    private String album;

    private Long usedSpace;

    private Long totalSpace;
}
