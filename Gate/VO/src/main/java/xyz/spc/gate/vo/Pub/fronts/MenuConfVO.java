package xyz.spc.gate.vo.Pub.fronts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuConfVO extends BaseVO {

    private String id;

    private String userId;

    private String userAccount;

    private String menuId;

    private String menuName;

    private Integer status;

    private Integer value;

}

