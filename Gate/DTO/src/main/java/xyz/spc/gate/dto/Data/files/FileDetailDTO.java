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
public class FileDetailDTO extends BaseDTO {

    private Long id;

    private String describe;

    private Long downloadTime;

    private Long size;

    private String path;

    private String diskPath;
}
