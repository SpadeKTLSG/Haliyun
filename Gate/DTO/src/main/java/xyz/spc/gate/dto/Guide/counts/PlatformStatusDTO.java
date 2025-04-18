package xyz.spc.gate.dto.Guide.counts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlatformStatusDTO extends BaseDTO {

    private Long id;

    private Long userCount;

    private Long clusterCount;

    private Long fileCount;

    private Long riskCount;
}
