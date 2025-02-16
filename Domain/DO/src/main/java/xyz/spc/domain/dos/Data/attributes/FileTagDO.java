package xyz.spc.domain.dos.Data.attributes;

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
@TableName("file_tag")
public class FileTagDO extends BaseDO {

    private Long id;

    private String name;

    private Integer status;
}
