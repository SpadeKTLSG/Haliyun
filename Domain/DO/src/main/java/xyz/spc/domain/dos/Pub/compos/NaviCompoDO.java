package xyz.spc.domain.dos.Pub.compos;

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
@TableName("nvi_compo")
public class NaviCompoDO extends BaseDO {


    private Long id;

    private String name;

    private String target;

    private Integer sort;
}
