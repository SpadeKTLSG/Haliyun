package xyz.spc.gate.dto.Group.groups;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO extends BaseDTO {

    private Long id;

    private String name;

    private String nickname;

    private String pic;

    private Integer popVolume;
}
