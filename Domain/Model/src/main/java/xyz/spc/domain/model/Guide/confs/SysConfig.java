package xyz.spc.domain.model.Guide.confs;


import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 系统配置
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysConfig extends BaseModel {


    private Long id;

    /**
     * 配置
     */
    private String conf;

    /**
     * 值
     */
    private String value;
}
