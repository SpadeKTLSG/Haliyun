package xyz.spc.gate.vo.Guide.feedbacks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackVO extends BaseVO {

    private Long id;

    private Long userId;

    private String userAccount;

    private String title;

    private String content;

}
