package xyz.spc.common.funcpack.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.spc.common.util.beanUtil.BeanUtil;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页工具类
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class PageUtil {

    /**
     * {@link PageRequest} to {@link Page}
     */
    public static Page convert(PageRequest pageRequest) {
        return convert(pageRequest.getCurrent(), pageRequest.getSize());
    }

    /**
     * {@link PageRequest} to {@link Page}
     */
    public static Page convert(long current, long size) {
        return new Page(current, size);
    }

    /**
     * {@link IPage} to {@link PageResponse}
     */
    public static PageResponse convert(IPage iPage) {
        return buildConventionPage(iPage);
    }

    /**
     * {@link IPage} to {@link PageResponse}
     */
    public static <TARGET, ORIGINAL> PageResponse<TARGET> convert(IPage<ORIGINAL> iPage, Class<TARGET> targetClass) {
        iPage.convert(each -> BeanUtil.convert(each, targetClass));
        return buildConventionPage(iPage);
    }

    /**
     * {@link IPage} to {@link PageResponse}
     */
    public static <TARGET, ORIGINAL> PageResponse<TARGET> convert(IPage<ORIGINAL> iPage, Function<? super ORIGINAL, ? extends TARGET> mapper) {
        List<TARGET> targetDataList = iPage.getRecords().stream()
                .map(mapper)
                .collect(Collectors.toList());
        return PageResponse.<TARGET>builder()
                .current((int) iPage.getCurrent())
                .size((int) iPage.getSize())
                .records(targetDataList)
                .total((int) iPage.getTotal())
                .build();
    }

    /**
     * {@link IPage} build to {@link PageResponse}
     */
    private static PageResponse buildConventionPage(IPage iPage) {
        return PageResponse.builder()
                .current((int) iPage.getCurrent())
                .size((int) iPage.getSize())
                .records(iPage.getRecords())
                .total((int) iPage.getTotal())
                .build();
    }
}
