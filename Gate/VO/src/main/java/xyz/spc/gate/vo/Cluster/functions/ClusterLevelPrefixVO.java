package xyz.spc.gate.vo.Cluster.functions;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterLevelPrefixVO extends BaseVO {


    private String id;

    private String clusterId;

    private String name;

    private Integer levelStart;

    private Integer levelEnd;

    private String polish;

    private String remark;
}
