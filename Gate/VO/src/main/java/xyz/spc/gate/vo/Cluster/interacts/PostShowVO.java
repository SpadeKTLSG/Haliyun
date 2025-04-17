package xyz.spc.gate.vo.Cluster.interacts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PostShowVO extends BaseVO {

    private String id;

    private String userId;

    // 群组的需要去查具体 ClusterDO 的 name
    private String clusterName;

    private Integer personShow;

    private String title;

    private String content;

    private String pics;
}
