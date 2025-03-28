package xyz.spc.gate.dto.Group.interacts;

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

    private Long groupId;

    private Integer personShow;

    private String title;

    private String content;

    private String pics;
}
