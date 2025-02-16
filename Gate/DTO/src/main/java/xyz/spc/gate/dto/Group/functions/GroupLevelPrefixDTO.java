package xyz.spc.gate.dto.Group.functions;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupLevelPrefixDTO extends BaseDTO {


    private Long id;

    private Long groupId;

    private String name;

    private Integer levelStart;

    private Integer levelEnd;

    private String polish;

    private String remark;
}
