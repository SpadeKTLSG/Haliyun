package xyz.spc.domain.model.Cluster.interacts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 评论
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Remark extends BaseModel {

    private Long id;

    /**
     * 类型对应的主键
     */
    private Long targetId;

    /**
     * 类型 0群组的评论 1文件的评论 2动态的评论
     */
    private Integer type;
    public static final int TYPE_GROUP = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_DYNAMIC = 2;

    /**
     * 点赞数 (可重复点击)
     */
    private Long likes;
}
