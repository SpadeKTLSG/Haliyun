package xyz.spc.domain.dos.Guide.logs;

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
@TableName("file_op_log")
public class FileOpLogDO extends BaseDO {

    private Long id;

    private Long userId;

    private Long fileId;

    private Integer op;

    private String account;

    private String fileName;
}
