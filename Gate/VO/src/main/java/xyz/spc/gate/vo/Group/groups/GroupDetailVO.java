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
public class GroupDetailVO extends BaseVO {


    private Long id;

    private String shareLink;

    private String album;

    private Long usedSpace;

    private Long totalSpace;
}
