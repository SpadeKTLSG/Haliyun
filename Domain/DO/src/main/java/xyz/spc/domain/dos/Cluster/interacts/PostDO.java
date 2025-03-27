package xyz.spc.domain.dos.Cluster.interacts;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("post")
public class PostDO extends BaseDO {

    private Long id;

    private Long userId;

    private Long clusterId;

    private Integer personShow;

    private String title;

    private String content;

    private String pics;
}
