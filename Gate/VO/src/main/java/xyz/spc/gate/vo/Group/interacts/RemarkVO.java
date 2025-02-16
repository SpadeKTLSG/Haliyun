package xyz.spc.gate.vo.Group.interacts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RemarkVO extends BaseVO {
    private Long id;

    private Long targetId;

    private Integer type;

    private Long likes;
}
