package xyz.spc.common.funcpack.page;

import lombok.Data;
import xyz.spc.common.constant.ReqRespCT;

/**
 * 分页请求对象 (别再拿来放接口了, 维护性太烂)
 */
@Data
public class PageRequest {


    /**
     * 当前页
     */
    private Integer current;

    /**
     * 每页显示条数
     */
    private Integer size;


    public PageRequest() {
        new PageRequest(1, ReqRespCT.DEFAULT_PAGE_SIZE);
    }

    public PageRequest(Integer current) {
        this.size = ReqRespCT.DEFAULT_PAGE_SIZE;
        new PageRequest(current, size);
    }

    public PageRequest(Integer current, Integer size) {
        this.current = current;
        this.size = size;
    }


}
