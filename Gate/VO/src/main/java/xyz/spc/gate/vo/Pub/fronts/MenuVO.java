package xyz.spc.gate.vo.Pub.fronts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO extends BaseVO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Integer admin;

    private String name;

    private String url;

    private Integer order;

    private String remark;

}
