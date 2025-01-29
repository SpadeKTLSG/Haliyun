package xyz.spc.common.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.lang.reflect.Array;
import java.util.*;

import static com.github.dozermapper.core.loader.api.TypeMappingOptions.mapEmptyString;
import static com.github.dozermapper.core.loader.api.TypeMappingOptions.mapNull;

/**
 * 对象属性复制工具类
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class BeanUtil {


    protected static Mapper BEAN_MAPPER_BUILDER;

    static {
        BEAN_MAPPER_BUILDER = DozerBeanMapperBuilder.buildDefault();
    }

    /**
     * 属性复制
     *
     * @param source 数据对象
     * @param target 目标对象
     * @param <T>    目标类型
     * @param <S>    源类型
     * @return 转换后对象
     */
    public static <T, S> T convert(S source, T target) {
        Optional.ofNullable(source).ifPresent(each -> BEAN_MAPPER_BUILDER.map(each, target));
        return target;
    }

    /**
     * 复制单个对象
     *
     * @param source 数据对象
     * @param clazz  复制目标类型
     * @param <T>    目标类型
     * @param <S>    源类型
     * @return 转换后对象
     */
    public static <T, S> T convert(S source, Class<T> clazz) {
        return Optional.ofNullable(source).map(each -> BEAN_MAPPER_BUILDER.map(each, clazz)).orElse(null);
    }

    /**
     * 复制多个对象
     *
     * @param sources 数据对象
     * @param clazz   复制目标类型
     * @param <T>     目标类型
     * @param <S>     源类型
     * @return 转换后对象集合
     */
    public static <T, S> List<T> convert(List<S> sources, Class<T> clazz) {
        return Optional.ofNullable(sources).map(each -> {
            List<T> targetList = new ArrayList<T>(each.size());
            each.stream().forEach(item -> targetList.add(BEAN_MAPPER_BUILDER.map(item, clazz)));
            return targetList;
        }).orElse(null);
    }

    /**
     * 复制多个对象
     *
     * @param sources 数据对象
     * @param clazz   复制目标类型
     * @param <T>     目标类型
     * @param <S>     源类型
     * @return 转换后对象集合
     */
    public static <T, S> Set<T> convert(Set<S> sources, Class<T> clazz) {
        return Optional.ofNullable(sources).map(each -> {
            Set<T> targetSize = new HashSet<T>(each.size());
            each.stream().forEach(item -> targetSize.add(BEAN_MAPPER_BUILDER.map(item, clazz)));
            return targetSize;
        }).orElse(null);
    }

    /**
     * 复制多个对象
     *
     * @param sources 数据对象
     * @param clazz   复制目标类型
     * @param <T>     目标类型
     * @param <S>     源类型
     * @return 转换后对象集合
     */
    public static <T, S> T[] convert(S[] sources, Class<T> clazz) {
        return Optional.ofNullable(sources).map(each -> {
            @SuppressWarnings("unchecked") T[] targetArray = (T[]) Array.newInstance(clazz, sources.length);
            for (int i = 0; i < targetArray.length; i++) {
                targetArray[i] = BEAN_MAPPER_BUILDER.map(sources[i], clazz);
            }
            return targetArray;
        }).orElse(null);
    }


    /**
     * 拷贝非空且非空串属性
     *
     * @param source 数据源
     * @param target 指向源
     */
    public static void convertIgnoreNullAndBlank(Object source, Object target) {
        DozerBeanMapperBuilder dozerBeanMapperBuilder = DozerBeanMapperBuilder.create();
        Mapper mapper = dozerBeanMapperBuilder.withMappingBuilders(new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(), mapNull(false), mapEmptyString(false));
            }
        }).build();
        mapper.map(source, target);
    }

    /**
     * 拷贝非空属性
     *
     * @param source 数据源
     * @param target 指向源
     */
    public static void convertIgnoreNull(Object source, Object target) {
        DozerBeanMapperBuilder dozerBeanMapperBuilder = DozerBeanMapperBuilder.create();
        Mapper mapper = dozerBeanMapperBuilder.withMappingBuilders(new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(), mapNull(false));
            }
        }).build();
        mapper.map(source, target);
    }

    /**
     * 使用非空属性列表来指定拷贝 非空属性
     *
     * @param source           数据源
     * @param target           指向源
     * @param ignoreProperties 忽略属性, 直接写名字
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanWrapper srcWrapper = PropertyAccessorFactory.forBeanPropertyAccess(source);
        BeanWrapper trgWrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);

        Set<String> ignoreSet = new HashSet<>();
        if (ignoreProperties != null) {
            Collections.addAll(ignoreSet, ignoreProperties);
        }

        for (java.beans.PropertyDescriptor descriptor : srcWrapper.getPropertyDescriptors()) {
            String propertyName = descriptor.getName();
            if (srcWrapper.isReadableProperty(propertyName) && trgWrapper.isWritableProperty(propertyName) && !ignoreSet.contains(propertyName)) {
                Object value = srcWrapper.getPropertyValue(propertyName);
                trgWrapper.setPropertyValue(propertyName, value);
            }
        }
    }


}
