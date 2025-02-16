package xyz.spc.gate.vo.Group.dispatchers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileStatusVO extends BaseVO {
    private Long id;

    private String fileType;

    private String typeRemark;

    private String remark;

    private Long count;

    private Long space;
}
