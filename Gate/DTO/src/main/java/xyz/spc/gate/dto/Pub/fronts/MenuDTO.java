package xyz.spc.gate.dto.Pub.fronts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO extends BaseDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Integer admin;

    private String name;

    private String url;

    private Integer score;

    private String remark;

}
