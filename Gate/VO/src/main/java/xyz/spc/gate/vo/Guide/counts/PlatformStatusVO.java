package xyz.spc.gate.vo.Guide.counts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlatformStatusVO extends BaseVO {

    private Long id;

    private Long userCount;

    private Long groupCount;

    private Long fileCount;

    private Long riskCount;
}
