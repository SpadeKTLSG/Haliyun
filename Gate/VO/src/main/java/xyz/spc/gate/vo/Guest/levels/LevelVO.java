package xyz.spc.gate.vo.Guest.levels;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LevelVO extends BaseVO {

    private Long id;

    private Integer floor;

    private String name;

    private Float cut;

    private Long grow;

    private String dscr;

    private String remark;

}
