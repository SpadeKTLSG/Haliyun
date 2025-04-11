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
@TableName("file_func")
public class FileFuncDO extends BaseDO {


    private Long id;

    private Long tag;

    private Long fileLock;

    private Integer status;

    private Integer validDateType;

    private String validDate;

}
