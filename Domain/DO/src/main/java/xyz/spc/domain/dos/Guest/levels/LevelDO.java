package xyz.spc.domain.dos.Guest.levels;

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
@TableName("level")
public class LevelDO extends BaseDO {

    private Long id;

    private Integer floor;

    private String name;

    private Float cut;

    private Long grow;

    private String dscr;

    private String remark;

}
