package xyz.spc.domain.model.Data.files;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 文件
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseModel {

    private Long id;

    /**
     * -> id 父项id (顶层为自己)
     */
    private Long pid;

    /**
     * -> User
     */
    private Long userId;

    /**
     * -> Cluster
     */
    private Long groupId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件类型 (就识别后缀)
     */
    private String type;

}
