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

    private String id;

    private String clusterId;

    private String senderId;

    private String receiverId;

    private Integer status;

    private Integer droped;

    private String header;

    private String body;


    //? Tmodel

    private String clusterName; // 群组名称

    private String senderName; // 发送者名称

    private String receiverName; // 接收人名称

}
