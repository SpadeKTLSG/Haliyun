package xyz.spc.common.util.fileUtil;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import xyz.spc.common.constant.UploadDownloadCT;
import xyz.spc.common.funcpack.uuid.IdUtil;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static xyz.spc.common.util.fileUtil.PathUtil.checkNameSecurity;
import static xyz.spc.common.util.fileUtil.PathUtil.checkPathSecurity;
import static xyz.spc.common.util.sysUtil.DateUtil.getCurrDate;

/**
 * 文件处理工具类
 */
@Slf4j
public final class FileUtil {

    /**
     * 文件名正则表达式
     */
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 路径分隔符
     */
    public static String PATH_SEPARATOR = "/";


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
     * @param type 文件类型
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeImportBytes(byte[] data, String type) throws IOException {
        return writeBytes(data, UploadDownloadCT.DOWNLOAD_DEFAULT_PATH, type);
    }

    /**
     * 写数据到文件中
     *
     * @param data      数据
     * @param uploadDir 目标文件
     * @param type      文件类型
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeBytes(byte[] data, String uploadDir, String type) throws IOException {
        FileOutputStream fos = null;
        String pathName;
        try {
            String extension = "." + type;
            pathName = getCurrDate() + "/" + IdUtil.fastUUID() + extension;
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
     * 判断是否为相同文件
     */
    private Boolean checkSameFile(File f1, File f2) {
        if (f1 == null && f2 == null) {
            return true;
        }
        if (f1 == null || f2 == null) {
            return false;
        }
        boolean same = false;
        if (f2.length() == f1.length()) {
            try {
                HashCode distHash = com.google.common.io.Files.asByteSource(f2).hash(Hashing.sha256());
                HashCode srcHash = com.google.common.io.Files.asByteSource(f1).hash(Hashing.sha256());
                if (distHash.equals(srcHash)) {
                    same = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return same;
    }


    /**
     * 新建文件
     */
    public boolean newFolder(String path, String name) {
        checkPathSecurity(path);
        checkNameSecurity(name);

        String fullPath = path + name;
        return cn.hutool.core.io.FileUtil.mkdir(fullPath) != null;
    }


    /**
     * 删除文件
     */
    public boolean deleteFile(String path, String name) {
        checkPathSecurity(path);
        checkNameSecurity(name);

        String fullPath = path + name;
        return cn.hutool.core.io.FileUtil.del(fullPath);
    }


    /**
     * 删除文件夹
     */
    public boolean deleteFolder(String path, String name) {
        checkPathSecurity(path);
        checkNameSecurity(name);

        return deleteFile(path, name);
    }

    /**
     * 重命名文件
     */
    public boolean renameFile(String path, String name, String newName) {
        checkPathSecurity(path);
        checkNameSecurity(name, newName);

        // 如果文件名没变则不做任何操作
        if (StrUtil.equals(name, newName)) {
            return true;
        }

        File file = new File(path + name);
        if (!file.exists()) {
            throw ExceptionUtil.wrapRuntime(new FileNotFoundException("文件夹不存在."));
        }

        return Objects.nonNull(cn.hutool.core.io.FileUtil.rename(file, newName, true));
    }

    /**
     * 重命名文件夹
     */
    public boolean renameFolder(String path, String name, String newName) {
        checkPathSecurity(path);
        checkNameSecurity(name, newName);

        return renameFile(path, name, newName);
    }

    /**
     * 复制文件
     */
    public boolean copyFile(String path, String name, String targetPath, String targetName, Boolean overwrite) {
        checkPathSecurity(path, targetPath);
        checkNameSecurity(name, targetName);
        String srcFileStr = path + PATH_SEPARATOR + name;
        File srcFile = new File(srcFileStr);
        String targetFileStr = targetPath + PATH_SEPARATOR + targetName;
        File targetFile = new File(targetFileStr);
        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
            return false;
        }
        if (!overwrite && targetFile.exists()) {
            return false;
        }
        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件复制异常，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName, e);
            return false;
        }
        log.info("文件复制成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
        return true;
    }

    /**
     * 复制文件夹
     */
    public boolean copyFolder(String path, String name, String targetPath, String targetName, Boolean overwrite) {
        checkPathSecurity(path, targetPath);
        checkNameSecurity(name, targetName);
        String srcFileStr = path + PATH_SEPARATOR + name;
        File srcFile = new File(srcFileStr);
        String targetFileStr = targetPath + PATH_SEPARATOR + targetName;
        File targetFile = new File(targetFileStr);
        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
            return false;
        }
        if (!overwrite && targetFile.exists()) {
            return false;
        }
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        File[] flist = srcFile.listFiles();
        if (flist == null) {
            return true;
        }
        boolean ret = true;
        for (File f : flist) {
            try {
                if (f.isFile()) {
                    File dstFile = new File(targetFileStr, f.getName());
                    FileUtils.copyFile(f, dstFile);
                } else {
                    String childSrcPath = path + PATH_SEPARATOR + name;
                    String childDstPath = targetPath + PATH_SEPARATOR + targetName;
                    copyFolder(childSrcPath, f.getName(), childDstPath, f.getName(), overwrite);
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("文件复制异常，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName, e);
                ret = false;
            }
        }
        log.info("目录复制成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
        return ret;
    }

    /**
     * 移动文件
     */
    public boolean moveFile(String path, String name, String targetPath, String targetName, Boolean overwrite) {
        checkPathSecurity(path, targetPath);
        checkNameSecurity(name, targetName);
        String srcFileStr = path + PATH_SEPARATOR + name;
        File srcFile = new File(srcFileStr);
        String targetFileStr = targetPath + PATH_SEPARATOR + targetName;
        File targetFile = new File(targetFileStr);
        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
            return false;
        }
        if (targetFile.exists()) {
            if (overwrite) {// 覆盖
                targetFile.delete();// 删除再移动
                srcFile.renameTo(targetFile);
            } else { // 不覆盖
                return false;
            }
        } else {
            srcFile.renameTo(targetFile);
        }
        log.info("文件移动成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
        return true;
    }

    /**
     * 移动文件夹
     */
    public static boolean moveFolder(String path, String name, String targetPath, String targetName, Boolean overwrite) {
        checkPathSecurity(path, targetPath);
        checkNameSecurity(name, targetName);
        String srcFileStr = path + PATH_SEPARATOR + name;
        File srcFile = new File(srcFileStr);
        String targetFileStr = targetPath + PATH_SEPARATOR + targetName;
        File targetFile = new File(targetFileStr);
        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
            return false;
        }
        if (targetFile.exists()) {
            if (overwrite) {
                // 移动子文件夹和子文件
                File[] flist = srcFile.listFiles();
                if (flist == null) {
                    return true;
                }
                for (File f : flist) {
                    if (f.isFile()) {
                        File dstFile = new File(targetFileStr, f.getName());
                        if (dstFile.exists()) {
                            dstFile.delete();// 删除再移动
                        }
                        f.renameTo(dstFile);
                    } else {
                        String childSrcPath = path + PATH_SEPARATOR + name;
                        String childDstPath = targetPath + PATH_SEPARATOR + targetName;
                        moveFolder(childSrcPath, f.getName(), childDstPath, f.getName(), overwrite);
                    }
                }
                // 移动子文件夹和子文件后，删除原文件夹
                srcFile.delete();
            } else { // 不覆盖
                return false;
            }
        } else {
            srcFile.renameTo(targetFile);
        }
        log.info("目录移动成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
        return true;
    }


    /**
     * 列出所有文件，包括子文件夹的文件
     */
    public static void listFiles(String path, List<String> result) {
        checkPathSecurity(path);
        String absFileStr = path;
        File absFile = new File(absFileStr);
        if (!absFile.exists()) {
            return;
        }
        File[] flist = absFile.listFiles();
        if (flist == null) {
            return;
        }

        for (File f : flist) {
            if (f.isFile()) {
                result.add(f.getName());
            } else {
                String childFilePath = path + PATH_SEPARATOR + f.getName();
                listFiles(childFilePath, result);
            }
        }
    }


}
