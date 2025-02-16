package xyz.spc.domain.dos.Money.props;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("prop")
public class PropDO extends BaseDO {

    private Long id;

    private Integer type;

    private String name;

    private String remark;

    private Integer price;

    private Integer stock;

    private String pic;

    private String attribute;
}
