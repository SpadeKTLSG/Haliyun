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
public class ReqLogVO extends BaseVO {

    private String id;

    private String userId;

    private String account;

    private String ipv4;

    private String ipv6;

    private Integer loginType;
}
