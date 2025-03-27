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
public class PostVO extends BaseVO {

    private Long id;

    private Long userId;

    private Long groupId;

    private Integer personShow;

    private String title;

    private String content;

    private String pics;
}
