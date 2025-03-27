package xyz.spc.domain.model.Cluster.managers;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 群组成员权限
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClusterAuth extends BaseModel {

    private Long id;

    /**
     * -> Cluster主键
     */
    private Long groupId;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * 踢人权限 0 Y 1 N
     */
    private Integer canKick;
    public static final int CAN_KICK_YES = 0;
    public static final int CAN_KICK_NO = 1;

    /**
     * 邀请权限 0 Y 1 N
     */
    private Integer canInvite;
    public static final int CAN_INVITE_YES = 0;
    public static final int CAN_INVITE_NO = 1;

    /**
     * 上传权限 0 Y 1 N
     */
    private Integer canUpload;
    public static final int CAN_UPLOAD_YES = 0;
    public static final int CAN_UPLOAD_NO = 1;

    /**
     * 下载权限 0 Y 1 N
     */
    private Integer canDownload;
    public static final int CAN_DOWNLOAD_YES = 0;
    public static final int CAN_DOWNLOAD_NO = 1;

    /**
     * 此权限启用情况 0 启用 1 关闭
     */
    private Integer status;
    public static final int STATUS_ENABLE = 0;
    public static final int STATUS_DISABLE = 1;

}
