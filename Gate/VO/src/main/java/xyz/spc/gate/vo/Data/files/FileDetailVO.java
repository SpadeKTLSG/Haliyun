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
public class FileDetailVO extends BaseVO {

    private String id;

    private String dscr;

    private Long downloadTime;

    private Long size;

    private String path;

    private String diskPath;
}
