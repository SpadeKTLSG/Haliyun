package xyz.spc.domain.model.Guide.risks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 风控
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Risklog extends BaseModel {


    private Long id;

    /**
     * 标题
     */
    private String header;

    /**
     * 条目id JSON
     */
    private String lines;
}
