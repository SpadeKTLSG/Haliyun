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

    private Long clusterId;

    private Long senderId;

    private Long receiverId;

    private Integer status;

    private Integer drop;

    private String header;

    private String body;


    //? Tmodel

    private String clusterName; // 群组名称

    private String senderName; // 发送者名称

    private String receiverName; // 接收人名称

}
