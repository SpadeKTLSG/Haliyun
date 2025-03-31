package xyz.spc.domain.model.Guest.records;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

import java.time.LocalDateTime;

/**
 * 坟墓
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tomb extends BaseModel {

    private Long id;

    /**
     * -> userId
     */
    private Long userId;

    /**
     * 用户注册排名
     */
    private Integer createNo;

    /**
     * 最大硬币持有量(官方的硬币)
     */
    private Long maxCoin;

    /**
     * 参加或创建的群组的最长生存时间
     */
    private String bigintestCluster;

    /**
     * 最晚登陆时间
     */
    private LocalDateTime deepestNignt;

    /**
     * 最早登陆时间
     */
    private LocalDateTime earlistMorning;

    /**
     * 下载过的最大文件大小
     */
    private Long maxSize;

    /**
     * 下载过的最小文件大小
     */
    private Long minSize;
}
