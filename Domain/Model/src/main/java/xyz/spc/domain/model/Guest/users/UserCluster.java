package xyz.spc.domain.model.Guest.users;


import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;


/**
 * 用户群组关联
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserCluster extends BaseModel {


    /**
     * id
     */
    private Long id;


    /**
     * -> User主键
     */
    private Long userId;

    /**
     * -> Cluster主键
     */
    private Long clusterId;

    /**
     * 集合名称
     */
    private String collect;
    public static final String COLLECT_DEFAULT = "默认群组集";

    /**
     * 排序
     */
    private Integer sort;


    //? 转换器

    //! 基础信息
}
