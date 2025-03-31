package xyz.spc.gate.vo.Cluster.functions;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO extends BaseVO {

    private Long id;

    private String name;

    private String content;

    private Integer readCount;
}
