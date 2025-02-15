package xyz.spc.domain.model.Money.donates;

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
public class Donate extends BaseModel {


    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 献金数
     */
    private Integer money;
    
    /**
     * 留言
     */
    private String remark;
}
