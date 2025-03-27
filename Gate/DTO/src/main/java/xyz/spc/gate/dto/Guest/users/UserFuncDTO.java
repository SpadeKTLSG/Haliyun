package xyz.spc.gate.dto.Guest.users;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserFuncDTO extends BaseDTO {

    private Long id;

    private Long levelId;

    private Integer vip;

    private Integer createClusterCount;

    private Integer createClusterMax;

    private Integer joinClusterCount;

    private Integer joinClusterMax;

    private Long coin;

    private Long energyCoin;

    private String registerCode;
}
