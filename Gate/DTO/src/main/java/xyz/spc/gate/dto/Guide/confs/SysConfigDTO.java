package xyz.spc.gate.dto.Guide.confs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigDTO extends BaseDTO {


    private Long id;

    private String conf;

    private String value;
}
