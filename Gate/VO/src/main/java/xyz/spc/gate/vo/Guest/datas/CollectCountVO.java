package xyz.spc.gate.vo.Guest.datas;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CollectCountVO extends BaseVO {

    private Long postCount;

    private Long fileCount;

    private Long clusterCount;
}
