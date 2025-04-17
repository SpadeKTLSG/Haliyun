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
public class ClusterVO extends BaseVO {

    private String id;

    private String creatorUserId;

    private String name;

    private String nickname;

    private String pic;

    private Integer popVolume;
}
