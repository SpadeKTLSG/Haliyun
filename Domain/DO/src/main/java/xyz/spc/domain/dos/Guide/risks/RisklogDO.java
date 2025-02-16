package xyz.spc.domain.dos.Guide.risks;

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
@TableName("risklog")
public class RisklogDO extends BaseDO {

    private Long id;

    private String header;

    private String lines;
}
