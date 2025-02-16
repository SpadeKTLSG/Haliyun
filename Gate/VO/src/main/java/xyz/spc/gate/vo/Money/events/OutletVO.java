package xyz.spc.gate.vo.Money.events;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OutletVO extends BaseVO {
    private Long id;

    private Integer status;

    private String name;

    private String intro;

    private String remark;

    private Integer type;

    private Long bonusExp;

    private Long bonusCoin;
}
