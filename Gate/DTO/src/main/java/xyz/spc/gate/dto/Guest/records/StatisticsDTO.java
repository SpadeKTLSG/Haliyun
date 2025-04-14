package xyz.spc.gate.dto.Guest.records;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO extends BaseDTO {

    private Long id;

    private Long userId;

    private Integer comment;

    private Integer download;

    private Integer upload;

    private Integer outlet;

    private Integer mail;

    private Integer collect;

    private Integer likes;

    private Integer trick;
}
