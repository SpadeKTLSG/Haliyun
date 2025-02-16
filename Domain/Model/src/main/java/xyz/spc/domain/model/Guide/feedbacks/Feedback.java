package xyz.spc.domain.model.Guide.feedbacks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;


/**
 * 反馈
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseModel {


    private Long id;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * -> User.account
     */
    private String userAccount;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

}
