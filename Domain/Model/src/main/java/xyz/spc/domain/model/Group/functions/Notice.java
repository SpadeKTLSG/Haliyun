package xyz.spc.domain.model.Group.functions;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 通知
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseModel {

    private Long id;

    /**
     * 标题
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 阅读次数
     */
    private Integer readCount;

}
