package xyz.spc.gate.dto.Guide.logs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileOpLogDTO extends BaseDTO {
    private Long id;

    private Long userId;

    private Long fileId;

    private Integer op;

    private String account;

    private String fileName;
}
