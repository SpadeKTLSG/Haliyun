package xyz.spc.domain.dos.Guide.confs;

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
@TableName("sys_config")
public class SysConfigDO extends BaseDO {


    private Long id;

    private String conf;

    private String value;
}
