package xyz.spc.common.funcpack.baddilter;

/**
 * @author Turbine
 * @Description
 * @date 2022/1/23 18:17
 */
public interface Filter<T> {
    //过滤字符串方法
    T filtration(T content);
}
