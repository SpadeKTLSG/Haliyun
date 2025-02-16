package xyz.spc.domain.model.Group.dispatchers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 群组文件状态
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileStatus extends BaseModel {

    private Long id;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件类型介绍
     */
    private String typeRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 计数
     */
    private Long count;

    /**
     * 占用容量(字节B)
     */
    private Long space;
}
