package xyz.spc.common.funcpack.page;

import lombok.Builder;
import lombok.Data;
import xyz.spc.common.constant.ReqRespCT;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页返回对象
 */
@Data
@Builder
public class PageResponse<T> implements Serializable {


    /**
     * 当前页
     */
    private Long current;

    /**
     * 每页显示条数
     */
    private Long size;

    /**
     * 总数
     */
    private Long total;

    /**
     * 查询数据列表
     */
    private List<T> records;

    public PageResponse(long current) {
        this(current, ReqRespCT.DEFAULT_PAGE_SIZE, 0);
    }

    public PageResponse(long current, long size) {
        this(current, size, 0);
    }

    public PageResponse(long current, long size, long total) {
        this(current, size, total, Collections.emptyList());
    }

    public PageResponse(long current, long size, long total, List<T> records) {
        if (current > 1) {
            this.current = current;
        }
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
