package xyz.spc.domain.model.Guide.logs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 请求日志
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReqLog extends BaseModel {

    private Long id;

    /**
     * 是否成功
     */
    private Integer success;
    public static final int SUCCESS_NO = 0;
    public static final int SUCCESS_YES = 1;

    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 请求类型
     */
    private String queryType;

    /**
     * 请求路径
     */
    private String route;

    /**
     * 计时(自带ms)
     */
    private String executeTime;
}
