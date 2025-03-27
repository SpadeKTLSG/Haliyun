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
@TableName("cluster_detail")
public class ClusterDetailDO extends BaseDO {

    private Long id;

    private String shareLink;

    private String album;

    private Long usedSpace;

    private Long totalSpace;

}
