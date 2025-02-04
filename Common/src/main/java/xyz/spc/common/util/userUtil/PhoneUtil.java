package xyz.spc.common.util.userUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号工具类
 */
public final class PhoneUtil {

    /**
     * 校验用户手机号是否合法
     */
    public static Boolean isMatches(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 校验用户手机号是否合法 - 弱校验
     */
    public static Boolean isMatches(String phone, boolean weak) {
        if (!weak) {
            return isMatches(phone);
        }
        //只要判断是否是11位数字即可
        if (phone.length() != 11) {
            return false;
        }
        return Boolean.TRUE;
    }

}
