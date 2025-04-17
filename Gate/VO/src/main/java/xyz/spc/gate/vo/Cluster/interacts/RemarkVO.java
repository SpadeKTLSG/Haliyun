package xyz.spc.gate.vo.Cluster.interacts;

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

    private String id;

    private Long targetId;

    private Integer type;

    private String content;

    private Long likes;
}
