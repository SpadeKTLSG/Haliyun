package xyz.spc.common.util.fileUtil;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import xyz.spc.common.constant.SystemCommonCT;
import xyz.spc.common.funcpack.uuid.IdUtils;
import xyz.spc.common.util.sysUtil.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

/**
 * 文件处理工具类
 */
public final class FileUtils {

    /**
     * 文件名正则表达式
     */
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.close(os);
            IOUtils.close(fis);
        }
    }

    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeImportBytes(byte[] data) throws IOException {
        return writeBytes(data, SystemCommonCT.DOWNLOAD_DEFAULT_PATH);
    }

    /**
     * 写数据到文件中
     *
     * @param data      数据
     * @param uploadDir 目标文件
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeBytes(byte[] data, String uploadDir) throws IOException {
        FileOutputStream fos = null;
        String pathName;
        try {
            String extension = getFileExtendName(data);
            pathName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
            File file = UploadUtil.getAbsoluteFile(uploadDir, pathName);
            fos = new FileOutputStream(file);
            fos.write(data);
        } finally {
            IOUtils.close(fos);
        }
        return UploadUtil.getPathFileName(uploadDir, pathName);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return 结果
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 下载文件名重新编码 UTF-8
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) {
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        String contentDispositionValue = "attachment; filename=" +
                percentEncodedFileName +
                ";" +
                "filename*=" +
                "utf-8''" +
                percentEncodedFileName;

        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue);
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8);
        return encode.replaceAll("\\+", "%20");
    }


    /**
     * 获取图像后缀
     *
     * @param photoByte 图像数据
     * @return 后缀名
     */
    public static String getFileExtendName(byte[] photoByte) {
        String strFileExtendName = "jpg";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97)) {
            strFileExtendName = "gif";
        } else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70)) {
            strFileExtendName = "jpg";
        } else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
            strFileExtendName = "bmp";
        } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
            strFileExtendName = "png";
        }
        return strFileExtendName;
    }


    /**
     * 获从文件全限定名中的文件名称 (带后缀类型)
     *
     * @param fileName 路径名称
     * @return 没有文件路径的名称
     */
    public static String getName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int lastUnixPos = fileName.lastIndexOf('/');
        int lastWindowsPos = fileName.lastIndexOf('\\');
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return fileName.substring(index + 1);
    }

    /**
     * 获取不带后缀文件名称
     *
     * @param fileName 路径名称
     * @return 没有文件路径和后缀的名称
     */
    public static String getNameNotSuffix(String fileName) {
        return fileName == null ? null : FilenameUtils.getBaseName(fileName);
    }


    /**
     * 获取文件后缀
     *
     * @param src 文件路径/名称 文件路径 C:\Users\Public\Pictures\Sample Pictures\test.jpg
     * @return 如果文件后缀 jpg
     */
    public static String getFileExt(String src) {
        String filename = src.substring(src.lastIndexOf(File.separator) + 1);// 获取到文件名

        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件名称，不带文件后缀部分
     *
     * @param src 文件路径 C:\Users\Public\Pictures\Sample Pictures\test.jpg
     * @return 文件名称 不带文件后缀 test
     */
    public static String getFileName(String src) {
        String filename = src.substring(src.lastIndexOf(File.separator) + 1);// 获取到文件名

        return filename.substring(0, filename.lastIndexOf("."));
    }


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
}
