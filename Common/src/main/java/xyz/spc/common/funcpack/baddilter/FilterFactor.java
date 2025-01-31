package xyz.spc.common.funcpack.baddilter;

import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * @author Turbine
 * @Description
 * @date 2022/1/23 18:54
 */
@Component
public class FilterFactor {

    private static volatile Filter filter;

    //DLC
    public Filter getResource(filterOpt option) {
        if (filter == null) {
            synchronized (FilterFactor.class) {
                //使用动态代理类反射创建对象
                if (filter == null) {
                    try {
                        Class<?> clazz = Class.forName(option.clazzName);
                        Constructor<?> constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        filter = (Filter) constructor.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return filter;
    }


    public enum filterOpt {
        AC_FILTER("com.turbine.tnd.utils.StringACFilter"), GEN_FILTER("com.turbine.tnd.utils.StringFilter");

        String clazzName;

        filterOpt(String stringFilter) {
            clazzName = stringFilter;
        }
    }

}
