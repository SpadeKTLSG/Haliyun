package xyz.spc.domain.model.Cluster.clusters;

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
public class Cluster extends BaseModel {

    private Long id;

    /**
     * 创建者用户id
     */
    private Long creatorUserId;

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
    public static final Integer BASIC_POP_VOLUME = 32;
    public static final Integer BASIC_POP_VOLUME_VIP = 1024;
}
