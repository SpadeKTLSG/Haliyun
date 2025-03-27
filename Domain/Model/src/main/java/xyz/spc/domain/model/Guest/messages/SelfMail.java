package xyz.spc.domain.model.Guest.messages;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 私信
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SelfMail extends BaseModel {
    
    private Long id;

    /**
     * -> Cluster主键
     */
    private Long groupId;

    /**
     * -> User主键 发送者
     */
    private Long senderId;

    /**
     * -> User主键 接收人
     */
    private Long receiverId;

    /**
     * 状态 0 创建未发送 1 已发送未投递 2 已投递未查看 3 已查看
     */
    private Integer status;
    public static final int STATUS_CREATE = 0;
    public static final int STATUS_SEND = 1;
    public static final int STATUS_DELIVER = 2;
    public static final int STATUS_READ = 3;

    /**
     * 删除 0 未删除 1 已删除 (任意一方)
     */
    private Integer drop;
    public static final int DROP_NO = 0;
    public static final int DROP_YES = 1;

    /**
     * 标题
     */
    private String header;

    /**
     * 正文
     */
    private String body;

}
