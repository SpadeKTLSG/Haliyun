package xyz.spc.domain.model.Cluster.dispatchers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

import java.time.LocalDateTime;

/**
 * 分享链接
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShareLink extends BaseModel {

    private Long id;

    /**
     * -> File主键
     */
    private Long fileId;

    /**
     * -> User.account 分享者账号
     */
    private String godAccount;

    /**
     * 过期日期
     */
    private LocalDateTime expireDate;

    /**
     * 阅读数
     */
    private Long visitCount;

}
