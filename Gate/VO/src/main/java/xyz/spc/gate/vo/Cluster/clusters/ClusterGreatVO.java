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
public class ClusterGreatVO extends BaseVO {

    //! id
    private Long id;

    //    ClusterVO

    private String name;

    private String nickname;

    private String pic;

    private Integer popVolume;


    //    ClusterDetailVO

    private String shareLink;

    private String album;

    private Long usedSpace;

    private Long totalSpace;


    //    ClusterFuncVO

    private Long noticeId;

    private Long currencyId;

    private Integer allowInvite;

    private Long currencyStock;

    private Long coinStock;

    private String remarks;

    // 临时字段
}
