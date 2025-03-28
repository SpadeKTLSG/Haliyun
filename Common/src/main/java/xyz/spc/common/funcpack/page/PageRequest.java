package xyz.spc.common.funcpack.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.spc.common.constant.ReqRespCT;

/**
 * 分页请求对象 (别再拿来放接口了, 维护性太烂)
 */
@Data
@AllArgsConstructor
public class PageRequest {

    /**
     * 当前页
     */
    private Integer current = 1;

    /**
     * 每页显示条数
     */
    private Integer size = ReqRespCT.DEFAULT_PAGE_SIZE;
}
