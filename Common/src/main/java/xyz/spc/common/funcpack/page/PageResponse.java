package xyz.spc.common.funcpack.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import xyz.spc.common.constant.ReqRespCT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页返回对象
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> implements Serializable {


    /**
     * 当前页
     */
    private Integer current;

    /**
     * 每页显示条数
     */
    private Integer size;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 查询数据列表
     */
    private List<T> records;

    public PageResponse() {

    }

    public PageResponse(int current) {
        this(current, ReqRespCT.DEFAULT_PAGE_SIZE, 0);
    }

    public PageResponse(int current, int size) {
        this(current, size, 0);
    }

    public PageResponse(int current, int size, int total) {
        this(current, size, total, new ArrayList<>());
    }

    public PageResponse(int current, int size, int total, List<T> records) {
        this.current = Math.max(current, 1);
        this.size = size;
        this.total = total;
        this.records = records;
    }

    public PageResponse<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R> PageResponse<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(Collectors.toList());
        return ((PageResponse<R>) this).setRecords(collect);
    }
}
