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
public class FileGreatDTO extends BaseDTO {


    private Long id;

    private Long pid;

    // FileVO

    private Long userId;

    private Long clusterId;

    private String name;

    private String type;


    // FileDetailVO

    private String dscr;

    private Long downloadTime;

    private Long size;

    private String path;

    private String diskPath;

    // FileFuncVO

    private Long tag;

    private Long fileLock;

    private Integer status;

    private Integer validDateType;

    private String validDate;
}
