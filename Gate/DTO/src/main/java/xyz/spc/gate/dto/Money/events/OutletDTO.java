package xyz.spc.gate.dto.Money.events;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OutletDTO extends BaseDTO {
    private Long id;

    private Integer status;

    private String name;

    private String intro;

    private String remark;

    private Integer type;

    private Long bonusExp;

    private Long bonusCoin;
}
