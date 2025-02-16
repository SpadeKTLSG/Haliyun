package xyz.spc.domain.dos.Group.managers;

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
@TableName("group_auth")
public class GroupAuthDO extends BaseDO {

    private Long id;

    private Long groupId;

    private Long userId;

    private Integer canKick;

    private Integer canInvite;

    private Integer canUpload;

    private Integer canDownload;

    private Integer status;

}
