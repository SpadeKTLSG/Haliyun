package xyz.spc.domain.dos.Data.files;

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
@TableName("file")
public class FileDO extends BaseDO {

    private Long id;

    private Long pid;

    private Long userId;

    private Long groupId;

    private String name;

    private String type;

}
