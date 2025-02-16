package xyz.spc.domain.model.Group.groups;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 群组
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Group extends BaseModel {

    private Long id;

    /**
     * 群组名称
     */
    private String name;

    /**
     * 群组昵称
     */
    private String nickname;

    /**
     * 图片
     */
    private String pic;

    /**
     * 群组容量
     */
    private Integer popVolume;
}
