package xyz.spc.gate.vo.Money.donates;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DonateVO extends BaseVO {
    private Long id;

    private String nickname;

    private Integer money;

    private String remark;

}

