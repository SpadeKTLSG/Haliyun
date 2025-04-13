package xyz.spc.domain.dos.Guest.messages;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("self_mail")
public class SelfMailDO extends BaseDO {

    private Long id;

    private Long clusterId;

    private Long senderId;

    private Long receiverId;

    private Integer status;

    private Integer droped;

    private String header;

    private String body;


}
