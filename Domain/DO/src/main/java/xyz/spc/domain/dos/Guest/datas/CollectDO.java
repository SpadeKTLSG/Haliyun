package xyz.spc.domain.dos.Guest.datas;

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
@TableName("collect")
public class CollectDO extends BaseDO {

    private Long id;

    private Long targetId;

    private Long userId;

    private Integer type;

}
