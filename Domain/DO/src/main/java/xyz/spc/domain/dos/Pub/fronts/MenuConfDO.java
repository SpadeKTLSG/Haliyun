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
@TableName("menu_conf")
public class MenuConfDO extends BaseDO {

    private Long id;

    private Long userId;

    private String userAccount;

    private Long menuId;

    private String menuName;

    private Integer status;

    private Integer value;

}
