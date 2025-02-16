package xyz.spc.gate.dto.Data.files;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO extends BaseDTO {

    private Long id;

    private Long pid;

    private Long userId;

    private Long groupId;

    private String name;

    private String type;
}
