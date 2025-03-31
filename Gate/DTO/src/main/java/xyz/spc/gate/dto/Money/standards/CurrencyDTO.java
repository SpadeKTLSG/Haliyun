package xyz.spc.gate.dto.Money.standards;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO extends BaseDTO {

    private Long id;

    private Long clusterId;

    private String name;

    private Float exchangeRate;

    private String pic;
}
