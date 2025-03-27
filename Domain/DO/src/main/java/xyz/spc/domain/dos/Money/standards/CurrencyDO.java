package xyz.spc.domain.dos.Money.standards;

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
@TableName("currency")
public class CurrencyDO extends BaseDO {

    private Long id;

    private Long clusterId;

    private String name;

    private Float exchangeRate;

    private String pic;
}
