package xyz.spc.gate.dto.Guide.logs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogDTO extends BaseDTO {

    private Long id;

    private Long userId;

    private String account;

    private String ipv4;

    private String ipv6;

    private Integer loginType;
}

