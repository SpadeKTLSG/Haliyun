package xyz.spc.gate.dto.Guide.feedbacks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO extends BaseDTO {

    private Long id;

    private Long userId;

    private String userAccount;

    private String title;

    private String content;

}
