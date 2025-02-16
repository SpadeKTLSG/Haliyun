package xyz.spc.gate.vo.Data.files;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileVO extends BaseVO {

    private Long id;

    private Long pid;

    private Long userId;

    private Long groupId;

    private String name;

    private String type;
}
