package xyz.spc.gate.dto.Guest.records;

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
public class TombDTO extends BaseDTO {

    private Long id;

    private Long userId;

    private Integer createNo;

    private Long maxCoin;

    private String bigintestCluster;

    private LocalDateTime deepestNignt;

    private LocalDateTime earlistMorning;

    private Long maxSize;

    private Long minSize;
}
