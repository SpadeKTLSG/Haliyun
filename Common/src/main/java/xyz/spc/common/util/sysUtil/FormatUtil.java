package xyz.spc.common.util.sysUtil;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式工具类
 */
public class FormatUtil {

    public static final Pattern shiFenMiaoPattern = Pattern.compile("(\\d{1,2}[：:]){0,2}\\d{1,2}");
    /**
     * 将下划线映射到骆驼命名使用的正则表达式, 预编译正则用于提高效率
     */
    private static final Pattern patternForUTC = Pattern.compile("_([a-z]){1}");

    public static String hiddenMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * @param s
     * @param isFen
     * @return
     */
    public static BigDecimal strToBigDecimal(String s, Boolean isFen) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        if (!NumberUtils.isNumber(s)) {
            return null;
        }
        BigDecimal bd = new BigDecimal(s);
        if (isFen != null && isFen.booleanValue()) {
            bd = bd.divide(new BigDecimal(100), RoundingMode.CEILING);
        }
        return bd;
    }

    public static Long shiFenMiaoToSeconds(String shifenmiao) {
        if (StringUtils.isBlank(shifenmiao)) {
            return 0L;
        }
        Long totalSeconds = 0L;
        shifenmiao = shifenmiao.replaceAll(" ", "");
        boolean matched = shiFenMiaoPattern.matcher(shifenmiao).matches();
        if (matched) {
            List<String> sfmList = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(shifenmiao, ":：");
            while (st.hasMoreTokens()) {
                sfmList.add(st.nextToken());
            }
            Collections.reverse(sfmList);
            String[] sfmArr = sfmList.toArray(new String[0]);
            for (int i = 0; i < sfmArr.length; i++) {
                if (i == 0) {
                    totalSeconds += Long.valueOf(sfmArr[i]);
                } else if (i == 1) {
                    totalSeconds += Long.valueOf(sfmArr[i]) * 60;
                } else if (i == 2) {
                    totalSeconds += Long.valueOf(sfmArr[i]) * 3600;
                }
            }
        }
        return totalSeconds;
    }

    /**
     * 将下划线映射到骆驼命名
     *
     * @param str
     * @return
     */
    public static String mapUnderscoreToCamelCase(String str) {
        // 先转成全小写
        str = str.toLowerCase();
        final Matcher matcher = patternForUTC.matcher(str);
        while (matcher.find()) {
            str = str.replaceAll(matcher.group(), matcher.group(1).toUpperCase());
        }
        return str;
    }

    /**
     * 将骆驼命名映射到下划线, 必须是标准的驼峰命名, 否则会出现奇怪的结果
     *
     * @param str
     * @return
     */
    public static String mapCamelCaseToUnderscore(String str) {
        return str.replaceAll("([A-Z]){1}", "_$1").toUpperCase();
    }
}

