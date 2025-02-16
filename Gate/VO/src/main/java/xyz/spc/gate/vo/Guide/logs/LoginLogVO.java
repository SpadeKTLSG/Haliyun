package xyz.spc.gate.vo.Guide.logs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogVO extends BaseVO {

    private Long id;

    private Long userId;

    private String account;

    private String ipv4;

    private String ipv6;

    private Integer loginType;
}

