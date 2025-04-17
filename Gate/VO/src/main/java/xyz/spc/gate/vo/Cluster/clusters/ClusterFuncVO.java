package xyz.spc.gate.vo.Cluster.clusters;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterFuncVO extends BaseVO {

    private String id;

    private String noticeId;

    private String currencyId;

    private Integer allowInvite;

    private Long currencyStock;

    private Long coinStock;

    private String remark;
}
