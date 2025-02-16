package xyz.spc.domain.model.Guide.feedbacks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 管理员公告
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminNotice extends BaseModel {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否置顶 0 否 1 是
     */
    private Integer top;
    public static final int TOP_NO = 0;
    public static final int TOP_YES = 1;

}
