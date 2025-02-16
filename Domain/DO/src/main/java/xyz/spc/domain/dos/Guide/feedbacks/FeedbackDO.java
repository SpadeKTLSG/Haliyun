package xyz.spc.domain.dos.Guide.feedbacks;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("feedback")
public class FeedbackDO extends BaseDO {

    private Long id;

    private Long userId;

    private String userAccount;

    private String title;

    private String content;

}
