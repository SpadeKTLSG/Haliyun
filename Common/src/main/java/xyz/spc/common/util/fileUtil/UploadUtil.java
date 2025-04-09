package xyz.spc.common.util.fileUtil;


import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.constant.UploadDownloadCT;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.util.collecUtil.StringUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import static xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil.nextId;

/**
 * 文件上传工具类
 */
public final class UploadUtil {


    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 成功后的文件全路径名称
     */
    public static String upload(MultipartFile file) throws IOException {
        try {
            return upload(UploadDownloadCT.UPLOAD_DEFAULT_PATH, file, MimeTypeUtil.DEFAULT_ALLOWED_EXTENSION, null);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir  相对应用的基目录
     * @param file     上传的文件
     * @param fileName 文件名称, 空就使用原本文件的名称
     * @return 成功后的文件全路径名称
     */
    public static String upload(String baseDir, MultipartFile file, String fileName) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtil.DEFAULT_ALLOWED_EXTENSION, fileName);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 超出最大大小/文件名太长
     * @throws IOException                    读写文件出错时
     */
    public static String upload(String baseDir, MultipartFile file, String[] allowedExtension, String fileName) throws IOException {

        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();

        // 文件名长度校验
        if (fileNamelength > UploadDownloadCT.DEFAULT_FILE_NAME_LENGTH) {
            throw new ClientException(String.valueOf(UploadDownloadCT.DEFAULT_FILE_NAME_LENGTH));
        }

        // 文件扩展名校验
        assertAllowed(file, allowedExtension);

        // 如果不指定文件名, 就用上传文件本身的文件名
        if (StringUtil.isBlank(fileName)) fileName = extractFilename(file);

        // 文件传递
        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();

        file.transferTo(Paths.get(absPath));


        return getPathFileName(baseDir, fileName);
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {

        // 使用自定义的编码方式:  原文件名 + "#" + 确定的随机ID + "." + 原扩展名
        return String.format("%s#%d.%s",
                FilenameUtils.getBaseName(file.getOriginalFilename()),
                nextId(),
                getExtension(file)
        );
    }

    /**
     * 解码文件名
     */
    public static String unExtractFilename(String fileName) {

        // 从上面自定义的编码方式中提取出原文件名
        String[] fileNameParts = fileName.split("#");

        // 源文件没有类型
        if (fileNameParts.length == 1) {
            return fileNameParts[0];
        }

        return fileNameParts[0] + "." + fileNameParts[1].split("\\.")[1];

    }


    /**
     * 获取文件的绝对路径
     *
     * @param uploadDir 上传目录
     * @param fileName  文件名
     */
    public static File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (desc.exists()) {
            throw new ClientException("文件已存在");
        }

        if (!desc.getParentFile().exists() && !desc.getParentFile().mkdirs()) {
            throw new IOException("目标文件的父文件夹不存在且创建失败");
        }

        return desc;
    }

    /**
     * 获取文件的相对路径
     *
     * @param uploadDir 上传目录
     * @param fileName  文件名
     * @return 相对路径
     */
    public static String getPathFileName(String uploadDir, String fileName) {
        int dirLastIndex = uploadDir.length() + 1;
        String currentDir = org.apache.commons.lang3.StringUtils.substring(uploadDir, dirLastIndex);
        return currentDir + "/" + fileName;
    }


    /**
     * 文件类型 (扩展名) 校验
     */
    public static void assertAllowed(MultipartFile file, String[] allowedExtension) throws ClientException {
        long size = file.getSize();
        if (size > UploadDownloadCT.DEFAULT_MAX_SIZE) {
            throw new ClientException(" " + UploadDownloadCT.DEFAULT_MAX_SIZE / 1024 / 1024 + "is Maximum");
        }

        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtil.IMAGE_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtil.FLASH_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtil.MEDIA_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtil.VIDEO_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtil.DOC_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtil.COMPRESS_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     */
    public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtil.isEmpty(extension)) {
            extension = MimeTypeUtil.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }
}
