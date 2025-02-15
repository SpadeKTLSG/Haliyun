package xyz.spc.domain.dos.Guide.feedbacks;

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
@TableName("admin_notice")
public class AdminNoticeDO extends BaseDO {

    private Long id;

    private String title;

    private String content;

    private Integer top;

}
