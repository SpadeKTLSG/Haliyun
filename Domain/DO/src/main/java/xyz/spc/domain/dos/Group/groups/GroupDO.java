package xyz.spc.domain.dos.Group.groups;

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
@TableName("group")
public class GroupDO extends BaseDO {

    private Long id;

    private String name;

    private String nickname;

    private String pic;

    private Integer popVolume;
}
