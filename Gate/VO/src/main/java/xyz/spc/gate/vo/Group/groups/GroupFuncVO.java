package xyz.spc.gate.vo.Group.groups;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupFuncVO extends BaseVO {

    private Long id;

    private Long noticeId;

    private Long currencyId;

    private Integer allowInvite;

    private Long currencyStock;

    private Long coinStock;

    private String remarks;
}
