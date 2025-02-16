package xyz.spc.gate.dto.Data.attributes;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileTagDTO extends BaseDTO {

    private Long id;

    private String name;

    private Integer status;
}
