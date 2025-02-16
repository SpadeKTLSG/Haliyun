package xyz.spc.gate.vo.Data.attributes;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileTagVO extends BaseVO {

    private Long id;

    private String name;

    private Integer status;
}
