package xyz.spc.gate.dto.Group.managers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupAuthDTO extends BaseDTO {

    private Long id;

    private Long groupId;

    private Long userId;

    private Integer canKick;

    private Integer canInvite;

    private Integer canUpload;

    private Integer canDownload;

    private Integer status;
}
