package xyz.spc.gate.vo.Guide.risks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RisklogVO extends BaseVO {

    private String id;

    private String header;

    private String lines;

}
