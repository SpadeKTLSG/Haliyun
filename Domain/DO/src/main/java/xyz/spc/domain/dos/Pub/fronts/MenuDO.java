package xyz.spc.domain.dos.Pub.fronts;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("menu")
public class MenuDO extends BaseDO {

    private Long id;

    private Long parentId;

    private Integer type;

    private Integer admin;

    private String name;

    private String url;

    private Integer order;

    private String remark;

}
