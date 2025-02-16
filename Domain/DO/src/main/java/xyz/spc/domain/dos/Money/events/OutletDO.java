package xyz.spc.domain.dos.Money.events;

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
@TableName("outlet")
public class OutletDO extends BaseDO {

    private Long id;

    private Integer status;

    private String name;

    private String intro;

    private String remark;

    private Integer type;

    private Long bonusExp;

    private Long bonusCoin;
}
