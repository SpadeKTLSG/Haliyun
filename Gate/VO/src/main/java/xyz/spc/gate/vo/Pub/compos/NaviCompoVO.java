package xyz.spc.gate.vo.Pub.compos;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class NaviCompoVO extends BaseVO {

    private Long id;

    private String name;

    private String target;

    private Integer sort;
}
