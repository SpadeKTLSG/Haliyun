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
public class ClusterAuthVO extends BaseVO {

    private Long id;

    private Long clusterId;

    private Long userId;

    private Integer canKick;

    private Integer canInvite;

    private Integer canUpload;

    private Integer canDownload;

    private Integer status;
}
