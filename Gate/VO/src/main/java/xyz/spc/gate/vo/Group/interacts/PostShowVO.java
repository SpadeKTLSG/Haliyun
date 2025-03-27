package xyz.spc.gate.vo.Group.interacts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PostShowVO extends BaseVO {

    private Long id;

    private Long userId;

    // 群组的需要去查具体 GroupDO 的 name
    private String groupName;

    private Integer personShow;

    private String title;

    private String content;

    private String pics;
}
