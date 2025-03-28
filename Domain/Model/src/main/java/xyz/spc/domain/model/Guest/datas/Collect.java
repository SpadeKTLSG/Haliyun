package xyz.spc.domain.model.Guest.datas;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 收藏
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Collect extends BaseModel {

    private Long id;

    /**
     * 收藏目标id
     */
    private Long targetId;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * 收藏类型 动态0 文件1 群组2
     */
    private Integer type;
    public static final int TYPE_POST = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_CLUSTER = 2;

}
