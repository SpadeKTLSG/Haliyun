package xyz.spc.gate.dto.Cluster.functions;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO extends BaseDTO {

    private Long id;

    private String name;

    private String content;

    private Integer readCount;
}
