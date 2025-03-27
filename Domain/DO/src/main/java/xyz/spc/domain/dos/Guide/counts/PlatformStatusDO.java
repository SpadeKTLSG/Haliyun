package xyz.spc.domain.dos.Guide.counts;

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
@TableName("platform_status")
public class PlatformStatusDO extends BaseDO {

    private Long id;

    private Long userCount;

    private Long clusterCount;

    private Long fileCount;

    private Long riskCount;
}
