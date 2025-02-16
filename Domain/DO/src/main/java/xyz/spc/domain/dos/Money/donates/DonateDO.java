package xyz.spc.domain.dos.Money.donates;

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
@TableName("donate")
public class DonateDO extends BaseDO {

    private Long id;

    private String nickname;

    private Integer money;

    private String remark;

}
