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
@TableName("file_lock")
public class FileLockDO extends BaseDO {

    private Long id;

    private Long groupId;

    private Long ownerId;

    private String ownerAccount;

    private Integer currencyType;

    private Integer lockType;

    private Integer levelLimit;

    private Integer cost;

}
