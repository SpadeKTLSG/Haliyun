package xyz.spc.gate.dto.Money.donates;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DonateDTO extends BaseDTO {
    private Long id;

    private String nickname;

    private Integer money;

    private String remark;

}

