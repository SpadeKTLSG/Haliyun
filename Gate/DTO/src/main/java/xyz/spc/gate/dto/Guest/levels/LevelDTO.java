package xyz.spc.gate.dto.Guest.levels;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LevelDTO extends BaseDTO {

    private Long id;

    private Integer floor;

    private String name;

    private Float cut;

    private Long grow;

    private String desc;

    private String remark;

}
