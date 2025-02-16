package xyz.spc.gate.vo.Data.tasks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DownloadTaskVO extends BaseVO {

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
