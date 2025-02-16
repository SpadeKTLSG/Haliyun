package xyz.spc.gate.dto.Group.dispatchers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileStatusDTO extends BaseDTO {
    private Long id;

    private String fileType;

    private String typeRemark;

    private String remark;

    private Long count;

    private Long space;
}
