package xyz.spc.gate.vo.Pub.systems;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryVO extends BaseVO {

    private Long id;

    private String name;

    private String code;
}
