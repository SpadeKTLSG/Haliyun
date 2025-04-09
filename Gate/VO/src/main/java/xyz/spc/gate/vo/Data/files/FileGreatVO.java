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
public class FileGreatVO extends BaseVO {


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
