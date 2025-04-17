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
public class UploadTaskVO extends BaseVO {

    private String id;

    private String fileId;

    private String fileName;

    private String pid;

    private String userId;

    private Integer status;

    private Long fileSizeTotal;

    private Long fileSizeOk;

    private String executor;

}
