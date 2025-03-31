package xyz.spc.gate.vo.Cluster.clusters;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

import java.util.List;

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


    // 临时字段 T-model

    // 用户信息

    private String userAccount;

    private Integer userisAdmin;


    // 公告信息

    private String noticeName;

    private String noticeContent;


    // 货币信息

    private String currencyName;

    private Float currencyExchangeRate;

    private String currencyPic;


    // 评论信息(清单)

    private List<String> content;
}
