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

    private Long id;

    private Long tag;

    private Long lock;

    private Integer status;

    private Integer validDateType;

    private String validDate;
}
