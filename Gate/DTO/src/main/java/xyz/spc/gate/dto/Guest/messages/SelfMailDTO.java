package xyz.spc.gate.dto.Guest.messages;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SelfMailDTO extends BaseDTO {

    private Long id;

    private Long groupId;

    private Long senderId;

    private Long receiverId;

    private Integer status;

    private Integer drop;

    private String header;

    private String body;

}
