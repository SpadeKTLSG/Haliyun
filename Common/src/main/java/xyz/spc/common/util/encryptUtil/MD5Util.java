package xyz.spc.common.util.encryptUtil;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;

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


    /*
     * 根据文件内容获取MD5:
     * 第一步获取文件的byte信息，
     * 第二步通过MessageDigest类进行MD5加密，
     * 第三步转换成16进制的MD5码值
     */

    //根据文件获取文件MD5
    public static String getFileMD5(File file) {
        String s = null;

        try {
            s = getFileMD5(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }

    //根据文件流获取文件MD5
    public static String getFileMD5(FileInputStream fis) {
        String s = null;
        try {
            s = DigestUtils.md5Hex(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }

    //根据输入流获取文件MD5
    public static String getFileMD5(InputStream fis) {
        String s = null;
        try {
            s = DigestUtils.md5Hex(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }

}
