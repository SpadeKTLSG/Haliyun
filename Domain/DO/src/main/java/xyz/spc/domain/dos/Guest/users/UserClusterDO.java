package xyz.spc.domain.dos.Guest.users;

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
@TableName("user_cluster")
public class UserClusterDO extends BaseDO {

    private Long id;

    private Long userId;

    private Long clusterId;

    private String collect;

    private Integer sort;
}
