package xyz.spc.domain.model.Cluster.functions;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 群组等级前缀
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterLevelPrefix extends BaseModel {

    private Long id;

    /**
     * 对象群组 -> Cluster
     */
    private Long groupId;

    /**
     * 等级配置名称
     */
    private String name;

    /**
     * 对应开始等级 -> Level.floor
     */
    private Integer levelStart;

    /**
     * 对应结束等级 -> Level.floor
     */
    private Integer levelEnd;

    /**
     * 美化的名称
     */
    private String polish;

    /**
     * 备注
     */
    private String remark;


}
