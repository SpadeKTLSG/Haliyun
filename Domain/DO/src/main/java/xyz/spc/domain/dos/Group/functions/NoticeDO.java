package xyz.spc.domain.dos.Group.functions;

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
@TableName("notice")
public class NoticeDO extends BaseDO {

    private Long id;

    private String name;

    private String content;

    private Integer readCount;
}
