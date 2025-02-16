package xyz.spc.domain.model.Pub.compos;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 导航组件
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class NaviCompo extends BaseModel {

    private Long id;

    /**
     * 导航名称
     */
    private String name;

    /**
     * URL
     */
    private String target;

    /**
     * 排序
     */
    private Integer sort;
}
