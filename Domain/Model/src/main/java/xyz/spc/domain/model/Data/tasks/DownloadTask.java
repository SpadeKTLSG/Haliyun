package xyz.spc.domain.model.Data.tasks;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 下载任务
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DownloadTask extends BaseModel {

    private Long id;

    /**
     * -> File主键
     */
    private Long fileId;

    /**
     * -> File.name
     */
    private String fileName;

    /**
     * -> File.pid
     */
    private Long pid;

    /**
     * -> User主键
     */
    private Long userId;

    /**
     * 状态 (0 未开始 1 运行 2 暂停 3 完成 4 取消 5 失败 6 队列等待 7 任务离线(节点下线))
     */
    private Integer status;
    public static final int STATUS_NOT_START = 0;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_PAUSE = 2;
    public static final int STATUS_FINISH = 3;
    public static final int STATUS_CANCEL = 4;
    public static final int STATUS_FAIL = 5;
    public static final int STATUS_QUEUE = 6;
    public static final int STATUS_OFFLINE = 7;

    /**
     * 总共需要下载的大小
     */
    private Long fileSizeTotal;

    /**
     * 已经下载的大小
     */
    private Long fileSizeOk;

    /**
     * 负责节点
     */
    private String executor;
    public static final String EXECUTOR_LOCAL = "local"; // 本地

}
