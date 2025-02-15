package xyz.spc.domain.model.Data.attributes;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 文件标签
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileTag extends BaseModel {

    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 状态   (0正常 / 1暂停 / 2冻结)
     */
    private Integer status;
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_PAUSE = 1;
    public static final int STATUS_FREEZE = 2;

}
