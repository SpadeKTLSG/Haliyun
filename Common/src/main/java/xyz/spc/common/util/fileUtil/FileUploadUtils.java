package xyz.spc.common.util.fileUtil;


import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.funcpack.commu.exception.ClientException;
import xyz.spc.common.util.stringUtil.Constants;
import xyz.spc.common.util.stringUtil.StringUtils;
import xyz.spc.common.util.uuid.Seq;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

/**
 * 文件上传工具类 todo 抽取常量
 */
public class FileUploadUtils {

    /**
     * 默认大小 100M
     */
    public static final long DEFAULT_MAX_SIZE = 99999L * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认用户上传的文件路径
     */
    private static final String UPLOAD_DEFAULT_PATH = "D:\\upload";


    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static String upload(MultipartFile file) throws IOException {
        try {
            return upload(UPLOAD_DEFAULT_PATH, true, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, null);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static String upload(String baseDir, boolean isDatePath, MultipartFile file, String fileName) throws IOException {
        try {
            return upload(baseDir, isDatePath, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, fileName);
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
    public static String upload(String baseDir, boolean isDatePath, MultipartFile file, String[] allowedExtension, String fileName) throws IOException {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new ClientException(String.valueOf(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH));
        }

        assertAllowed(file, allowedExtension);

        if (StringUtils.isBlank(fileName)) fileName = extractFilename(file, isDatePath);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir, fileName);
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file, boolean isDatePath) {
        if (isDatePath) {
            return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(), FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), getExtension(file));
        }
        return StringUtils.format("{}_{}.{}", FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), getExtension(file));
    }


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

    public static String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = uploadDir.length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    /**
     * 文件大小校验
     */
    public static void assertAllowed(MultipartFile file, String[] allowedExtension) throws ClientException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new ClientException(" " + DEFAULT_MAX_SIZE / 1024 / 1024 + "is Maximum");
        }

        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtils.DOC_EXTENSION) {
                throw new ClientException("ONLY" + Arrays.toString(allowedExtension) + "is allowed");
            } else if (allowedExtension == MimeTypeUtils.COMPRESS_EXTENSION) {
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
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }
}
