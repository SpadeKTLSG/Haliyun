package xyz.spc.domain.dos.Guest.records;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tomb")
public class TombDO extends BaseDO {

    private Long id;

    private Long userId;

    private Long createNo;

    private Long maxCoin;

    private Long bigintestCluster;

    @TableField("deepest_night")
    private LocalDateTime deepestNignt;

    private LocalDateTime earlistMorning;

    private Long maxSize;

    private Long minSize;

}
