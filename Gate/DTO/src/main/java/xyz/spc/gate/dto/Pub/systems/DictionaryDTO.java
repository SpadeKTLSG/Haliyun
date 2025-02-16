package xyz.spc.gate.dto.Pub.systems;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryDTO extends BaseDTO {

    private Long id;

    private String name;

    private String code;
}
