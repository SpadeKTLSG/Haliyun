package xyz.spc.common.funcpack.checkcode;

import java.util.Random;

import static xyz.spc.common.constant.LoginCheckCT.DEFAULT_LENGTH;

/**
 * 数字字母简单验证码生成器
 */
public final class NumberLetterCheckCodeGenerator {


    public static String generateCheckCode() {
        return generateCheckCode(DEFAULT_LENGTH);
    }

    public static String generateCheckCode(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
