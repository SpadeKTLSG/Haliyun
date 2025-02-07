//package fileOP;
//
//import cn.hutool.core.exceptions.ExceptionUtil;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.io.IoUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import com.google.common.hash.HashCode;
//import com.google.common.hash.Hashing;
//import im.zhaojun.zfile.core.constant.ZFileConstant;
//import im.zhaojun.zfile.core.util.RequestHolder;
//import im.zhaojun.zfile.core.util.StringUtils;
//import im.zhaojun.zfile.module.config.service.SystemConfigService;
//import im.zhaojun.zfile.module.storage.model.enums.FileTypeEnum;
//import im.zhaojun.zfile.module.storage.model.enums.StorageTypeEnum;
//import im.zhaojun.zfile.module.storage.model.result.FileItemResult;
//import im.zhaojun.zfile.module.storage.service.StorageTaskSerice;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@SuppressWarnings("all")
//@Slf4j
//public class LocalServiceImpl {
//
//    private static final String PREVIEW_PARAM_NAME = "preview";
//
//    @Value("${zfile.img.thumbnail.path}")
//    private String thumbnail;
//
//    @Value("${zfile.img.thumbnail.min.size:512000}")
//    private long MIN_IMAGE_LEN;
//    @Value("${map.baidu.apiKey}")
//    private String MAP_BAIDU_APIKEY;
//    private List<String> imgSuffixs;
//    private List<String> audioSuffix;
//    private List<String> videoSuffix;
//    private List<String> textSuffix;
//    private List<String> officeSuffix;
//    @Autowired
//    StorageTaskSerice storageTaskSerice;
//    @Autowired
//    SystemConfigService systemConfigService;
//
//
//    public List<FileItemResult> fileList(String folderPath) throws IOException {
//        checkPathSecurity(folderPath);
//
//        List<FileItemResult> fileItemList = new ArrayList<>();
//
//        String fullPath = StringUtils.concat(param.getFilePath() + folderPath);
//        File file = new File(fullPath);
//        if (!file.exists()) {
//            throw new FileNotFoundException("文件不存在");
//        }
//
//        File[] files = file.listFiles();
//        if (files == null) {
//            return fileItemList;
//        }
//        for (File f : files) {
//            FileItemResult item = fileToFileItem(f, folderPath);
//
//            fileItemList.add(item);
//        }
//        // 检查缩略图
//        if (param.getEnableThumbnail()) {
//            initThumbnail(folderPath, false);
//        }
//
//        return fileItemList;
//    }
//
//    public FileItemResult getFileItem(String pathAndName) {
//        checkPathSecurity(pathAndName);
//
//        String fullPath = StringUtils.concat(param.getFilePath(), pathAndName);
//
//        File file = new File(fullPath);
//
//        if (!file.exists()) {
//            throw ExceptionUtil.wrapRuntime(new FileNotFoundException("文件不存在"));
//        }
//
//        String folderPath = StringUtils.getParentPath(pathAndName);
//        return fileToFileItem(file, folderPath);
//    }
//
//
//    public boolean newFolder(String path, String name) {
//        checkPathSecurity(path);
//        checkNameSecurity(name);
//
//        String fullPath = StringUtils.concat(param.getFilePath(), path, name);
//        return FileUtil.mkdir(fullPath) != null;
//    }
//
//
//    public boolean deleteFile(String path, String name) {
//        checkPathSecurity(path);
//        checkNameSecurity(name);
//
//        String fullPath = StringUtils.concat(param.getFilePath(), path, name);
//        return FileUtil.del(fullPath);
//    }
//
//
//    public boolean deleteFolder(String path, String name) {
//        checkPathSecurity(path);
//        checkNameSecurity(name);
//
//        return deleteFile(path, name);
//    }
//
//
//    public boolean renameFile(String path, String name, String newName) {
//        checkPathSecurity(path);
//        checkNameSecurity(name, newName);
//
//        // 如果文件名没变，不做任何操作.
//        if (StrUtil.equals(name, newName)) {
//            return true;
//        }
//
//        String srcPath = StringUtils.concat(param.getFilePath(), path, name);
//        File file = new File(srcPath);
//
//        boolean srcExists = file.exists();
//        if (!srcExists) {
//            throw ExceptionUtil.wrapRuntime(new FileNotFoundException("文件夹不存在."));
//        }
//
//        FileUtil.rename(file, newName, true);
//        return true;
//    }
//
//
//    public boolean renameFolder(String path, String name, String newName) {
//        checkPathSecurity(path);
//        checkNameSecurity(name, newName);
//
//        return renameFile(path, name, newName);
//    }
//
//
//    public StorageTypeEnum getStorageTypeEnum() {
//        return StorageTypeEnum.LOCAL;
//    }
//
//
//    public void uploadFile(String pathAndName, InputStream inputStream) {
//        checkPathSecurity(pathAndName);
//
//        String baseFilePath = param.getFilePath();
//        String uploadPath = StringUtils.removeDuplicateSlashes(baseFilePath + ZFileConstant.PATH_SEPARATOR + pathAndName);
//        // 如果目录不存在则创建
//        String parentPath = FileUtil.getParent(uploadPath, 1);
//        if (!FileUtil.exist(parentPath)) {
//            FileUtil.mkdir(parentPath);
//        }
//
//        File uploadToFileObj = new File(uploadPath);
//        BufferedOutputStream outputStream = FileUtil.getOutputStream(uploadToFileObj);
//        IoUtil.copy(inputStream, outputStream);
//        IoUtil.close(outputStream);
//        IoUtil.close(inputStream);
//    }
//
//
//    public ResponseEntity<Resource> downloadToStream(String pathAndName, Boolean isThumbnail) {
//        checkPathSecurity(pathAndName);
//        File file = null;
//        if (isThumbnail != null && isThumbnail) {
//            String thumbnailFullPath = StringUtils.concat(this.thumbnail, this.getName(), pathAndName);
//            file = new File(StringUtils.removeDuplicateSlashes(thumbnailFullPath));
//            if (!file.exists()) {
//                file = new File(StringUtils.removeDuplicateSlashes(param.getFilePath() + ZFileConstant.PATH_SEPARATOR + pathAndName));
//            }
//        } else {
//            file = new File(StringUtils.removeDuplicateSlashes(param.getFilePath() + ZFileConstant.PATH_SEPARATOR + pathAndName));
//        }
//        if (!file.exists()) {
//            ByteArrayResource byteArrayResource = new ByteArrayResource("文件不存在或异常，请联系管理员.".getBytes(StandardCharsets.UTF_8));
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .body(byteArrayResource);
//        }
//
//        HttpServletRequest request = RequestHolder.getRequest();
//        String type = request.getParameter("type");
//
//        MediaType mimeType = MediaType.APPLICATION_OCTET_STREAM;
//        if (StrUtil.equals(type, PREVIEW_PARAM_NAME)) {
//            mimeType = MediaTypeFactory.getMediaType(file.getName()).orElse(MediaType.APPLICATION_OCTET_STREAM);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//
//        if (ObjectUtil.equals(mimeType, MediaType.APPLICATION_OCTET_STREAM)) {
//            String fileName = file.getName();
//            headers.setContentDispositionFormData("attachment", StringUtils.encodeAllIgnoreSlashes(fileName));
//        }
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(mimeType)
//                .body(new FileSystemResource(file));
//    }
//
//
//    private FileItemResult fileToFileItem(File file, String folderPath) {
//        FileItemResult fileItemResult = new FileItemResult();
//        fileItemResult.setType(file.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.FILE);
//        fileItemResult.setTime(new Date(file.lastModified()));
//        fileItemResult.setSize(file.length());
//        fileItemResult.setName(file.getName());
//        fileItemResult.setPath(folderPath);
//
//        if (fileItemResult.getType() == FileTypeEnum.FILE) {
//            fileItemResult.setUrl(getDownloadUrl(StringUtils.concat(folderPath, file.getName())));
//            fileItemResult.setThumbnail(fileItemResult.getUrl() + "&thumbnail=true");
//        } else {
//            fileItemResult.setSize(null);
//        }
//        return fileItemResult;
//    }
//
//
//    /**
//     * 检查路径合法性：
//     * - 只有以 . 开头的允许通过，其他的如 ./ ../ 的都是非法获取上层文件夹内容的路径.
//     *
//     * @param paths 文件路径
//     * @throws IllegalArgumentException 文件路径包含非法字符时会抛出此异常
//     */
//    private static void checkPathSecurity(String... paths) {
//        for (String path : paths) {
//            // 路径中不能包含 .. 不然可能会获取到上层文件夹的内容
//            if (StrUtil.startWith(path, "/..") || StrUtil.containsAny(path, "../", "..\\")) {
//                throw new IllegalArgumentException("文件路径存在安全隐患: " + path);
//            }
//        }
//    }
//
//
//    /**
//     * 检查路径合法性：
//     * - 不为空，且不包含 \ / 字符
//     *
//     * @param names 文件路径
//     * @throws IllegalArgumentException 文件名包含非法字符时会抛出此异常
//     */
//    private static void checkNameSecurity(String... names) {
//        for (String name : names) {
//            // 路径中不能包含 .. 不然可能会获取到上层文件夹的内容
//            if (StrUtil.containsAny(name, "\\", "/")) {
//                throw new IllegalArgumentException("文件名存在安全隐患: " + name);
//            }
//        }
//    }
//
//
//    public boolean copyFile(String path, String name, String targetPath, String targetName, Boolean overwrite) {
//        checkPathSecurity(path, targetPath);
//        checkNameSecurity(name, targetName);
//        String srcFileStr = StringUtils.concat(param.getFilePath() + path + ZFileConstant.PATH_SEPARATOR + name);
//        File srcFile = new File(srcFileStr);
//        String targetFileStr = StringUtils.concat(param.getFilePath() + targetPath + ZFileConstant.PATH_SEPARATOR + targetName);
//        File targetFile = new File(targetFileStr);
//        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
//            return false;
//        }
//        if (!overwrite && targetFile.exists()) {
//            return false;
//        }
//        try {
//            FileUtils.copyFile(srcFile, targetFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("文件复制异常，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName, e);
//            return false;
//        }
//        log.info("文件复制成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
//        return true;
//    }
//
//
//    public boolean copyFolder(String path, String name, String targetPath, String targetName, Boolean overwrite) {
//        checkPathSecurity(path, targetPath);
//        checkNameSecurity(name, targetName);
//        String srcFileStr = StringUtils.concat(param.getFilePath() + path + ZFileConstant.PATH_SEPARATOR + name);
//        File srcFile = new File(srcFileStr);
//        String targetFileStr = StringUtils.concat(param.getFilePath() + targetPath + ZFileConstant.PATH_SEPARATOR + targetName);
//        File targetFile = new File(targetFileStr);
//        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
//            return false;
//        }
//        if (!overwrite && targetFile.exists()) {
//            return false;
//        }
//        if (!targetFile.exists()) {
//            targetFile.mkdir();
//        }
//        File[] flist = srcFile.listFiles();
//        if (flist == null) {
//            return true;
//        }
//        boolean ret = true;
//        for (File f : flist) {
//            try {
//                if (f.isFile()) {
//                    File dstFile = new File(targetFileStr, f.getName());
//                    FileUtils.copyFile(f, dstFile);
//                } else {
//                    String childSrcPath = StringUtils.concat(path + ZFileConstant.PATH_SEPARATOR + name);
//                    String childDstPath = StringUtils.concat(targetPath + ZFileConstant.PATH_SEPARATOR + targetName);
//                    copyFolder(childSrcPath, f.getName(), childDstPath, f.getName(), overwrite);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                log.error("文件复制异常，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName, e);
//                ret = false;
//            }
//        }
//        log.info("目录复制成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
//        return ret;
//    }
//
//
//    public boolean moveFile(String path, String name, String targetPath, String targetName, Boolean overwrite) {
//        checkPathSecurity(path, targetPath);
//        checkNameSecurity(name, targetName);
//        String srcFileStr = StringUtils.concat(param.getFilePath() + path + ZFileConstant.PATH_SEPARATOR + name);
//        File srcFile = new File(srcFileStr);
//        String targetFileStr = StringUtils.concat(param.getFilePath() + targetPath + ZFileConstant.PATH_SEPARATOR + targetName);
//        File targetFile = new File(targetFileStr);
//        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
//            return false;
//        }
//        if (targetFile.exists()) {
//            if (overwrite) {// 覆盖
//                targetFile.delete();// 删除再移动
//                srcFile.renameTo(targetFile);
//            } else { // 不覆盖
//                return false;
//            }
//        } else {
//            srcFile.renameTo(targetFile);
//        }
//        log.info("文件移动成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
//        return true;
//    }
//
//
//    public boolean moveFolder(String path, String name, String targetPath, String targetName, Boolean overwrite) {
//        checkPathSecurity(path, targetPath);
//        checkNameSecurity(name, targetName);
//        String srcFileStr = StringUtils.concat(param.getFilePath() + path + ZFileConstant.PATH_SEPARATOR + name);
//        File srcFile = new File(srcFileStr);
//        String targetFileStr = StringUtils.concat(param.getFilePath() + targetPath + ZFileConstant.PATH_SEPARATOR + targetName);
//        File targetFile = new File(targetFileStr);
//        if (!srcFile.exists() || !targetFile.getParentFile().exists()) {
//            return false;
//        }
//        if (targetFile.exists()) {
//            if (overwrite) {
//                // 移动子文件夹和子文件
//                File[] flist = srcFile.listFiles();
//                if (flist == null) {
//                    return true;
//                }
//                for (File f : flist) {
//                    if (f.isFile()) {
//                        File dstFile = new File(targetFileStr, f.getName());
//                        if (dstFile.exists()) {
//                            dstFile.delete();// 删除再移动
//                        }
//                        f.renameTo(dstFile);
//                    } else {
//                        String childSrcPath = StringUtils.concat(path + ZFileConstant.PATH_SEPARATOR + name);
//                        String childDstPath = StringUtils.concat(targetPath + ZFileConstant.PATH_SEPARATOR + targetName);
//                        moveFolder(childSrcPath, f.getName(), childDstPath, f.getName(), overwrite);
//                    }
//                }
//                // 移动子文件夹和子文件后，删除原文件夹
//                srcFile.delete();
//            } else { // 不覆盖
//                return false;
//            }
//        } else {
//            srcFile.renameTo(targetFile);
//        }
//        log.info("目录移动成功，源目录：[{}],源文件名：[{}],目标目录：[{}],目标文件：[{}]", path, name, targetPath, targetName);
//        return true;
//    }
//
//
//    // 列出所有文件，包括子文件夹的文件
//    private void listFiles(String path, List<FileItemResult> result) {
//        checkPathSecurity(path);
//        String absFileStr = StringUtils.concat(param.getFilePath() + path);
//        File absFile = new File(absFileStr);
//        if (!absFile.exists()) {
//            return;
//        }
//        File[] flist = absFile.listFiles();
//        if (flist == null) {
//            return;
//        }
//
//        for (File f : flist) {
//            if (f.isFile()) {
//                FileItemResult item = fileToFileItem(f, path);
//                result.add(item);
//            } else {
//                String childFilePath = StringUtils.concat(path + ZFileConstant.PATH_SEPARATOR + f.getName());
//                listFiles(childFilePath, result);
//            }
//        }
//    }
//
//    // 判断是否为相同文件
//    private Boolean checkSameFile(File f1, File f2) {
//        if (f1 == null && f2 == null) {
//            return true;
//        }
//        if (f1 == null || f2 == null) {
//            return false;
//        }
//        boolean same = false;
//        if (f2.length() == f1.length()) {
//            try {
//                HashCode distHash = com.google.common.io.Files.asByteSource(f2).hash(Hashing.sha256());
//                HashCode srcHash = com.google.common.io.Files.asByteSource(f1).hash(Hashing.sha256());
//                if (distHash.equals(srcHash)) {
//                    same = true;
//                }
//            } catch (IOException e) {
//                log.error(String.valueOf(e.getStackTrace()));
//            }
//        }
//        return same;
//    }
//}
