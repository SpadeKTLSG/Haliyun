package xyz.spc.domain.dos.Data.tasks;


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
@TableName("download_task")
public class DownloadTaskDO extends BaseDO {

    private Long id;

    private Long fileId;

    private String fileName;

    private Long pid;

    private Long userId;

    private Integer status;

    private Long fileSizeTotal;

    private Long fileSizeOk;

    private String executor;

}
