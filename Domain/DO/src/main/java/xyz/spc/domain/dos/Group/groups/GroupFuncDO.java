package xyz.spc.domain.dos.Group.groups;

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
@TableName("group")
public class GroupFuncDO extends BaseDO {

    private Long id;

    private Long noticeId;

    private Long currencyId;

    private Integer allowInvite;

    private Long currencyStock;

    private Long coinStock;

    private String remarks;
}
