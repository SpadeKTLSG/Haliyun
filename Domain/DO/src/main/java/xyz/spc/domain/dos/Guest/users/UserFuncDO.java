package xyz.spc.domain.dos.Guest.users;

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
@TableName("user_func")
public class UserFuncDO extends BaseDO {

    private Long id;

    private Long levelId;

    private Integer vip;

    private Integer createClusterCount;

    private Integer createClusterMax;

    private Integer joinClusterCount;

    private Integer joinClusterMax;

    private Long coin;

    private Long energyCoin;

    private String registerCode;

}
