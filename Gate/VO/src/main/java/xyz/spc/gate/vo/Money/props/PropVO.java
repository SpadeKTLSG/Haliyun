package xyz.spc.gate.vo.Money.props;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PropVO extends BaseVO {

    private String id;

    private Integer type;

    private String name;

    private String remark;

    private Integer price;

    private Integer stock;

    private String pic;

    private String attribute;

}
