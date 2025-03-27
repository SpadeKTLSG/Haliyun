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
public class ClusterDTO extends BaseDTO {

    private Long id;

    private String name;

    private String nickname;

    private String pic;

    private Integer popVolume;
}
