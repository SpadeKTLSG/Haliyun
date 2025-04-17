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


    private String id;

    private String pid;

    // FileVO

    private String userId;

    private String clusterId;

    private String name;

    private String type;


    // FileDetailVO

    private String dscr;

    private Long downloadTime;

    private Long size;

    private String path;

    private String diskPath;

    // FileFuncVO

    private String tag;

    private String fileLock;

    private Integer status;

    private Integer validDateType;

    private String validDate;
}
