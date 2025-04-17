package xyz.spc.domain.dos.Guest.records;

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
@TableName("statistics")
public class StatisticsDO extends BaseDO {

    private Long id;

    private Long userId;

    private Integer comment;

    private Integer download;

    private Integer upload;

    private Integer outlet;

    private Integer mail;

    private Integer collect;

    private Integer likes;

    private Integer trick;


}
