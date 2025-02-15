package xyz.spc.domain.dos.Group.dispatchers;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("file_status")
public class ShareLinkDO extends BaseDO {

    private Long id;

    private Long fileId;

    private String godAccount;

    private LocalDateTime expireDate;

    private Long visitCount;
}
