package xyz.spc.gate.dto.Cluster.interacts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO extends BaseDTO {
    private Long id;

    private Long userId;

    private Long clusterId;

    private Integer personShow;

    private String title;

    private String content;

    private String pics;
}
