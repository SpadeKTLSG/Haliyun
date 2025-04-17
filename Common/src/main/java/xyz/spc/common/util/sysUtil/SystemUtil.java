package xyz.spc.common.util.sysUtil;

import lombok.Getter;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 系统工具
 */
public final class SystemUtil {

    /**
     * 获取虚拟机的编码格式 可由-Dfile.encoding参数指定
     */
    @Getter
    private static final String encoding = Charset.defaultCharset().displayName();
    /**
     * 获取操作系统一般情况下默认的编码: Linux是utf-8 Windows是gbk
     */
    @Getter
    private static final String OSDefaultEncoding = isWindows() ? "gbk" : "utf-8";

    /**
     * 是否是Windows系统
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * 获取当前文件夹占用空间，单位GB，2位小数
     *
     * @param dirname 目录名
     * @return 占用空间 字节
     */
    public static long getDirSpaceSize(String dirname) {
        long dirlength = 0;
        File dir = new File(dirname);
        // 判断是否是目录,如果不是返回0
        if (dir.isDirectory()) {
            String[] f = dir.list();
            if (null == f) {
                return 0;
            }
            File f1;
            for (String s : f) {
                f1 = new File(dirname + "/" + s);
                if (!f1.isDirectory()) {
                    dirlength += f1.length();
                } else {
                    // 如果是目录,递归调用
                    dirlength += getDirSpaceSize(dirname + "/" + s);
                }
            }
        }
        return dirlength;
    }


    /**
     * 获取当前Path磁盘总空间和剩余空间 单位GB
     */
    public static String getDiskSpaceSize(String fileRootPath) {
        File file = new File(fileRootPath);
        String totalSpace = String.valueOf(file.getTotalSpace() / 1024 / 1024 / 1024);
        String freeSpace = String.valueOf(file.getFreeSpace() / 1024 / 1024 / 1024);

        return "总空间：" + totalSpace + "GB" + " 剩余空间：" + freeSpace + "GB";
    }


}
