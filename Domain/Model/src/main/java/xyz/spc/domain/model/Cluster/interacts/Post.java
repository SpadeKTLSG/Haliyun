package xyz.spc.domain.model.Cluster.interacts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 动态
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseModel {

    private Long id;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * -> Cluster主键
     */
    private Long clusterId;

    /**
     * 同步个人主页 0 否 1 是
     */
    private Integer personShow;
    public static final int PERSON_SHOW_NO = 0;
    public static final int PERSON_SHOW_YES = 1;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 逗号分隔的图片对象
     */
    private String pics;

}
