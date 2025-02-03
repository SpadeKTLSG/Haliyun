package xyz.spc.common.funcpack.commu.page;

import lombok.Data;
import xyz.spc.common.constant.SysRespCT;

/**
 * 分页请求对象
 */
@Data

public class PageRequest {

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页显示条数
     */
    private Long size = SysRespCT.DEFAULT_PAGE_SIZE;
}
