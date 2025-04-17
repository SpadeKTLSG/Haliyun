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

    private String id;

    private String clusterId;

    private String name;

    private Float exchangeRate;

    private String pic;
}
