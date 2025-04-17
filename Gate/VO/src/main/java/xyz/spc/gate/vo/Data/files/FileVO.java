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

    private String id;

    private String pid;

    private String userId;

    private String clusterId;

    private String name;

    private String type;
}
