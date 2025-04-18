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
public class ClusterAuthDTO extends BaseDTO {

    private Long id;

    private Long clusterId;

    private Long userId;

    private Integer canKick;

    private Integer canInvite;

    private Integer canUpload;

    private Integer canDownload;

    private Integer status;
}
