package xyz.spc.gate.dto.Pub.compos;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class NaviCompoDTO extends BaseDTO {

    private Long id;

    private String name;

    private String target;

    private Integer sort;
}
