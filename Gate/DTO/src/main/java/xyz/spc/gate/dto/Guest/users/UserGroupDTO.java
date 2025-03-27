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
public class UserClusterDTO extends BaseDTO {

    private Long id;

    private Long userId;

    private Long groupId;

    private String collect;

    private Integer sort;
}
