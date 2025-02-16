package xyz.spc.domain.model.Data.files;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 文件详情
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileDetail extends BaseModel {

    /**
     * -> File
     */
    private Long id;

    /**
     * 描述
     */
    private String desc;

    /**
     * 下载次数
     */
    private Long downloadTime;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 路径 (主要还是用父项id, 这个展示用记录)
     */
    private String path;

    /**
     * HDFS磁盘Path
     */
    private String diskPath;


}
