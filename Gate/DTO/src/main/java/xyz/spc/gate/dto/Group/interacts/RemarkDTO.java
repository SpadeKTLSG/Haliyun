package xyz.spc.gate.dto.Cluster.interacts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RemarkDTO extends BaseDTO {
    private Long id;

    private Long targetId;

    private Integer type;

    private Long likes;
}
