package xyz.spc.domain.dos.Pub.systems;

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
@TableName("dictionary")
public class DictionaryDO extends BaseDO {


    private Long id;

    private String name;

    private String code;
}
