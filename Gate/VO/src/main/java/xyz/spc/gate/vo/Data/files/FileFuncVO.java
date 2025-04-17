package xyz.spc.gate.vo.Data.files;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileFuncVO extends BaseVO {

    private String id;

    private String tag;

    private String fileLock;

    private Integer status;

    private Integer validDateType;

    private String validDate;
}
