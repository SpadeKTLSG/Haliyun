package xyz.spc.domain.dos.Guide.logs;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("req_log")
public class ReqLogDO extends BaseDO {

    private Long id;

    private Long userId;

    private String account;

    private String ipv4;

    private String ipv6;

    private Integer loginType;
}
