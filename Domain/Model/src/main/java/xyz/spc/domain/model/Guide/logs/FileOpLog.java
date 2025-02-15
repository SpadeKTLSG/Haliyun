package xyz.spc.domain.model.Guide.logs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 文件操作日志
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileOpLog extends BaseModel {

    private Long id;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * -> File主键
     */
    private Long fileId;

    /**
     * 操作类型 0上传 1下载 2删除 3分享
     */
    private Integer op;
    public static final int OP_UPLOAD = 0;
    public static final int OP_DOWNLOAD = 1;
    public static final int OP_DELETE = 2;
    public static final int OP_SHARE = 3;

    /**
     * User账号
     */
    private String account;

    /**
     * 文件名称
     */
    private String fileName;

}
