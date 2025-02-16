package xyz.spc.gate.vo.Guide.logs;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileOpLogVO extends BaseVO {
    private Long id;

    private Long userId;

    private Long fileId;

    private Integer op;

    private String account;

    private String fileName;
}
