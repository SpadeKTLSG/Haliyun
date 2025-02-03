package xyz.spc.common.util.encryptUtil;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * MD5加密工具类
 */
public final class MD5Util {


    /**
     * 加密
     */
    public static String enryption(String content) {
        return DigestUtils.md5Hex(content);
    }

    /**
     * salt加密
     */
    public static String enryption(String content, String salt) {
        return DigestUtils.md5Hex((salt + content).getBytes());
    }

    /**
     * 随机盐值加密
     */
    public static String randomSaltEnryption(String content) {
        return enryption(RandomStringUtils.randomAscii(8) + content);
    }


    /**
     * 验证
     *
     * @param target 待验证的串
     * @param md5    md5密文串
     */
    public static boolean verification(String target, String md5) {
        return md5.equals(enryption(target));
    }

    /**
     * salt验证
     *
     * @param target 待验证的串
     * @param md5    md5密文串
     */
    public static boolean verification(String target, String md5, String key) {
        return md5.equals(enryption(target, key));
    }

}
