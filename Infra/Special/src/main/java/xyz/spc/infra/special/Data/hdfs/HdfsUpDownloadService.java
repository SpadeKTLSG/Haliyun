//package xyz.spc.infra.special.Data.hdfs;
//
//
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IOUtils;
//import org.apache.hadoop.util.Progressable;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import xyz.spc.common.funcpack.Result;
//import xyz.spc.test.HdfsConn;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Service
//@Transactional(rollbackFor = Exception.class)
//public class HdfsUpDownloadService {
//
//    @Value("${fileRootPath}")
//    public String fileRootPath;
//
//    @Value("${tempPath}")
//    public String tempPath;
//
//    @Value("${size}")
//    public int size;
//
//    @Value("${secretLen}")
//    private int secretLen;
//
//    public Result hdfsUpload(HttpServletRequest request, MultipartFile file, String path) {
//        if (path == null) {
//            path = "/";
//        }
//        if (file.isEmpty()) {
//            return Result.fail("请选择要上传的文件！");
//        }
//        // 获取用户名
//        String userName = WebUtil.getUserNameByRequest(request);
//        // 上传文件
//        boolean b = false;
//        // 服务器上传的文件所在路径
//        String saveFilePath = fileRootPath + userName + "/" + path;
//        // 判断文件夹是否存在-建立文件夹
//        File filePathDir = new File(saveFilePath);
//        if (!filePathDir.exists()) {
//            filePathDir.mkdir();
//        }
//        // 获取上传文件的原名
//        String saveFileName = file.getOriginalFilename();
//        // 上传文件到-磁盘
//        try {
//            OutputStream outputStream = HdfsConn.getFileSystem().create(new Path(saveFilePath, saveFileName), new Progressable() {
//                @Override
//                public void progress() {
//                }
//            });
//            IOUtils.copyBytes(file.getInputStream(), outputStream, 2048, true);
//            b = true;
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (b) {
//            return Result.success("上传成功");
//        } else {
//            return Result.fail("上传失败");
//        }
//    }
//
//    public Result hdfsDownload(String fileName, String path, HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>(2, 1);
//        if (path == null) {
//            path = "/";
//        }
//        if (fileName.isEmpty()) {
//            return Result.fail("文件名为空！");
//        }
//        // 获取用户名
//        String userName = UserHolder.getUserNameByRequest(request);
//        // 下载文件，获取下载路径,这个是 个映射的路径
//        // 服务器下载的文件所在的本地路径的文件夹
//        String saveFilePath = fileRootPath + userName + "/" + path;
//        // 判断文件夹是否存在-建立文件夹
//        File filePathDir = new File(saveFilePath);
//        if (!filePathDir.exists()) {
//            filePathDir.mkdir();
//        }
//
//        // 本地路径
//        String saveFilePath1 = saveFilePath + "/" + fileName;
//        String link = saveFilePath1.replace(fileRootPath, "/data/");
//        link = stringSlashToOne(link);
//
//        try {
//            if (HdfsConn.getFileSystem().exists(new Path(saveFilePath, fileName))) {
//                FSDataInputStream inputStream = HdfsConn.getFileSystem().open(new Path(saveFilePath));
//                OutputStream outputStream = new FileOutputStream(link);
//                IOUtils.copyBytes(inputStream, outputStream, 4096, true);
//                return Result.success();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Result.fail("下载失败！");
//    }
//}
