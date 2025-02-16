package xyz.spc.gate.dto.Guide.risks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RisklogLineDTO extends BaseDTO {
    private Long id;

    private String header;

    private String content;
}
