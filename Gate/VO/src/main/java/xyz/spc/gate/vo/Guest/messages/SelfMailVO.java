package xyz.spc.gate.vo.Guest.messages;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SelfMailVO extends BaseVO {

    private Long id;

    private Long groupId;

    private Long senderId;

    private Long receiverId;

    private Integer status;

    private Integer drop;

    private String header;

    private String body;

}
