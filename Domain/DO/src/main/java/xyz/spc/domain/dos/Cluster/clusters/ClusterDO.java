package xyz.spc.domain.dos.Cluster.clusters;

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
@TableName("cluster")
public class ClusterDO extends BaseDO {

    private Long id;

    private Long creatorUserId;

    private String name;

    private String nickname;

    private String pic;

    private Integer popVolume;
}
