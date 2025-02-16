package xyz.spc.domain.model.Pub.systems;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 数据字典
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary extends BaseModel {


    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 代码
     */
    private String code;
}
