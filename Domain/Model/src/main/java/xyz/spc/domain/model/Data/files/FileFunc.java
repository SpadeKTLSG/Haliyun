package xyz.spc.domain.model.Data.files;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 文件功能
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileFunc extends BaseModel {

    private Long id;

    /**
     * -> Tag 标签
     */
    private Long tag;

    /**
     * -> Lock 锁
     */
    private Long lock;

    /**
     * 状态 (0正常, 1停用, 2封禁)
     */
    private Integer status;
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_STOP = 1;
    public static final int STATUS_BAN = 2;

    /**
     * 有效期类型 0：永久有效 1：自定义
     */
    private Integer validDateType;
    public static final int VALID_DATE_TYPE_FOREVER = 0;
    public static final int VALID_DATE_TYPE_CUSTOM = 1;

    /**
     * 有效期至
     */
    private String validDate;
}
