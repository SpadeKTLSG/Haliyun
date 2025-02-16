package xyz.spc.gate.dto.Money.props;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PropDTO extends BaseDTO {
    private Long id;

    private Integer type;

    private String name;

    private String remark;

    private Integer price;

    private Integer stock;

    private String pic;

    private String attribute;
}
