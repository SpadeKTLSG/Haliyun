package xyz.spc.gate.vo.Guide.confs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigVO extends BaseVO {


    private Long id;

    private String conf;

    private String value;
}
