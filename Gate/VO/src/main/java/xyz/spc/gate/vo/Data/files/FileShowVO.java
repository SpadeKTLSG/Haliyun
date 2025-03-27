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
public class FileShowVO extends BaseVO {

    private Long id;

    private Long pid;

    private Long userId;

    // 群组的需要去查具体 ClusterDO 的 name
    private String groupName;

    private String name;

    private String type;
}
