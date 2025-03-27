package xyz.spc.domain.dos.Cluster.dispatchers;

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
@TableName("file_status")
public class FileStatusDO extends BaseDO {

    private Long id;

    private String fileType;

    private String typeRemark;

    private String remark;

    private Long count;

    private Long space;
}
