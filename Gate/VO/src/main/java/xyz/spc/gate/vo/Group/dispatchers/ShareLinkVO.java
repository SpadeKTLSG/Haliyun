package xyz.spc.gate.vo.Group.dispatchers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShareLinkVO extends BaseVO {

    private Long id;

    private Long fileId;

    private String godAccount;

    private LocalDateTime expireDate;

    private Long visitCount;
}
