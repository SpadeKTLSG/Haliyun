package xyz.spc.gate.vo.Guest.datas;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CollectVO extends BaseVO {

    private Long id;

    private Long targetId;

    private Long userId;

    private Integer type;

}
