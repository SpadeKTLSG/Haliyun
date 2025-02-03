package xyz.spc.common.util.userUtil;

import xyz.spc.common.constant.LoginCommonCT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 验证码工具类
 */
public final class codeUtil {

    /**
     * 生成简单自定义验证码
     */
    public static String achieveCode() {  //由于数字 1 、 0 和字母 O 、l 有时分不清楚，所以，没有数字 1 、 0
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z"};
        List<String> list = Arrays.asList(beforeShuffle);//将String转换为List
        Collections.shuffle(list);  //因为要打乱顺序，所以才把String转为list，因为集合里面才有shuffle;
        return String.join("", list).substring(2, 2 + LoginCommonCT.DEFAULT_LENGTH);
    }


}
