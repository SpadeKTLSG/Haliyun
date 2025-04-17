package xyz.spc.gate.vo.Cluster.dispatchers;

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

    private String id;

    private String fileId;

    private String godAccount;

    private LocalDateTime expireDate;

    private Long visitCount;
}
