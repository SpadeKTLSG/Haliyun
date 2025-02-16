package xyz.spc.gate.dto.Data.files;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileFuncDTO extends BaseDTO {

    private Long id;

    private Long tag;

    private Long lock;

    private Integer status;

    private Integer validDateType;

    private String validDate;
}
