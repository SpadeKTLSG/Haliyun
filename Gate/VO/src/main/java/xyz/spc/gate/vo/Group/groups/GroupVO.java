package xyz.spc.gate.vo.Group.groups;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupVO extends BaseVO {

    private Long id;

    private String name;

    private String nickname;

    private String pic;

    private Integer popVolume;
}
