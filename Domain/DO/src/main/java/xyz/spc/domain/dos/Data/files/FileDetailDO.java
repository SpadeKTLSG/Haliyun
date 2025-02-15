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
@TableName("file_detail")
public class FileDetailDO extends BaseDO {


    private Long id;

    private String desc;

    private Long downloadTime;

    private Long size;

    private String path;

    private String diskPath;


}
