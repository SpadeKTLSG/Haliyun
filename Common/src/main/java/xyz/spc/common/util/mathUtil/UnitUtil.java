package xyz.spc.common.util.mathUtil;

import cn.hutool.core.util.NumberUtil;

import java.text.DecimalFormat;

public final class UnitUtil {


    /**
     * 转换为美化的文件大小
     */
    public static String toButifSize(double size) {
        String sizeStr = "";
        int level = 0;
        while (size > 1024) {
            size = size / 1024.0;
            level++;
        }
        sizeStr = switch (level) {
            case 0 -> "B";
            case 1 -> "KB";
            case 2 -> "MB";
            case 3 -> "GB";
            case 4 -> "TB";
            case 5 -> "PB";
            default -> sizeStr;
        };

        sizeStr = new DecimalFormat("######0.00").format(size) + sizeStr;
        return sizeStr;
    }

    /**
     * 转化为GB单位大小
     */
    public static float toGBSize(long size) {
        return (float) size / 1024 / 1024 / 1024;
    }

    /**
     * 转化为GB单位大小 - 取Long整数
     */
    public static long toGBSize4Long(long size) {
        return size / 1024 / 1024 / 1024;
    }

    /**
     * 将文件大小转换为可读单位
     *
     * @param bytes 字节数
     * @return 文件大小可读单位
     */
    public static String bytesToSize(long bytes) {
        if (bytes == 0) {
            return "0";
        }
        double k = 1024;
        String[] sizes = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        double i = Math.floor(Math.log(bytes) / Math.log(k));
        return NumberUtil.round(bytes / Math.pow(k, i), 3) + " " + sizes[(int) i];
    }

    /**
     * 字节大小转文件大小GB、MB、KB
     */
    public static String getByteToSize(long size) {

        StringBuffer bytes = new StringBuffer();
        // 保留两位有效数字
        DecimalFormat format = new DecimalFormat("###.00");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

}
