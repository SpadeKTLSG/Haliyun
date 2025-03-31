package xyz.spc.domain.model.Guide.counts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;


/**
 * 平台状态
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlatformStatus extends BaseModel {

    private Long id;

    /**
     * 用户数统计
     */
    private Long userCount;

    /**
     * 群组数统计
     */
    private Long clusterCount;

    /**
     * 文件数统计
     */
    private Long fileCount;

    /**
     * 风控数统计
     */
    private Long riskCount;
}
