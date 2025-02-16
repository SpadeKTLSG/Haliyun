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
public class GroupDetailDO extends BaseDO {

    private Long id;

    private String shareLink;

    private String album;

    private Long usedSpace;

    private Long totalSpace;

}
