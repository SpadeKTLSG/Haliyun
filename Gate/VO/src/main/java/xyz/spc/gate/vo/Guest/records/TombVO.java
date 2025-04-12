package xyz.spc.gate.vo.Guest.records;

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
public class TombVO extends BaseVO {

    private Long id;

    private Long userId;

    private Long createNo;

    private Long maxCoin;

    private Long bigintestCluster;

    private LocalDateTime deepestNignt;

    private LocalDateTime earlistMorning;

    private Long maxSize;

    private Long minSize;
}
