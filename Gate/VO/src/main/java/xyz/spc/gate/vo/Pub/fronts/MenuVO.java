package xyz.spc.gate.vo.Pub.fronts;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO extends BaseVO {

    private String id;

    private String parentId;

    private Integer type;

    private Integer admin;

    private String name;

    private String url;

    private Integer score;

    private String remark;

    /**
     * 存储区 Tmodel
     */
    private List<?> list;

    /**
     * 父菜单名称 Tmodel
     */
    private String parentName;

}
