package xyz.spc.gate.vo.Guest.records;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsVO extends BaseVO {

    private Long id;

    private Long userId;

    private Integer comment;

    private Integer download;

    private Integer upload;

    private Integer outlet;

    private Integer mail;

    private Integer collect;

    private Integer like;

    private Integer trick;
}
