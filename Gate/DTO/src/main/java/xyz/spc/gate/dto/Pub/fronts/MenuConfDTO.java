package xyz.spc.gate.dto.Pub.fronts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuConfDTO extends BaseDTO {

    private Long id;

    private Long userId;

    private String userAccount;

    private Long menuId;

    private String menuName;

    private Integer status;

    private Integer value;

}

