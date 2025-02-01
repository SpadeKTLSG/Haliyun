package xyz.spc.domain.dos.Guest;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

/**
 * 用户功能 DO
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_func")
public class UserFuncDO extends BaseDO {
}
