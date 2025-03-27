package xyz.spc.domain.model.Cluster.groups;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 群组详情
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterDetail extends BaseModel {

    private Long id;

    /**
     * 分享链接
     */
    private String shareLink;

    /**
     * 相册
     */
    private String album;

    /**
     * 使用容量
     */
    private Long usedSpace;

    /**
     * 总容量
     */
    private Long totalSpace;

}
