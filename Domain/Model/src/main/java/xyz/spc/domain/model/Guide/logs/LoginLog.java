package xyz.spc.domain.model.Guide.logs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;


/**
 * 登陆日志
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog extends BaseModel {

    private Long id;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * User账号
     */
    private String account;

    /**
     * IPv4的记录
     */
    private String ipv4;

    /**
     * IPv6的记录
     */
    private String ipv6;

    /**
     * 登陆方式 -> User.loginType
     */
    private Integer loginType;


}
