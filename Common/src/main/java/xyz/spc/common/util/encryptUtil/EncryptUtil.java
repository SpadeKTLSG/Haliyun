package xyz.spc.common.util.encryptUtil;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加密工具类
 */
public class EncryptUtil {

    /**
     * Base64编码
     */
    public static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64解码
     */
    public static byte[] decodeBase64(String str) {
        return Base64.getDecoder().decode(str);
    }

    /**
     * Base64编码
     */
    public static String encodeUTF8StringBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64解码
     */
    public static String decodeUTF8StringBase64(String str) {

        byte[] bytes = Base64.getDecoder().decode(str);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * URL编码
     */
    public static String encodeURL(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }


    /**
     * URL解码
     */
    public static String decodeURL(String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

}
