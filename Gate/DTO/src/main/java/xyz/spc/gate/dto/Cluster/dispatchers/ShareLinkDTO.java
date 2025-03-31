package xyz.spc.gate.dto.Cluster.dispatchers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShareLinkDTO extends BaseDTO {

    private Long id;

    private Long fileId;

    private String godAccount;

    private LocalDateTime expireDate;

    private Long visitCount;
}
