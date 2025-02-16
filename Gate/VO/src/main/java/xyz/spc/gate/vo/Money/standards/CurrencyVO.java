package xyz.spc.gate.vo.Money.standards;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyVO extends BaseVO {

    private Long id;

    private Long groupId;

    private String name;

    private Float exchangeRate;

    private String pic;
}
