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
@TableName("remark")
public class RemarkDO extends BaseDO {

    private Long id;

    private Long targetId;

    private Integer type;

    private Long likes;
}
