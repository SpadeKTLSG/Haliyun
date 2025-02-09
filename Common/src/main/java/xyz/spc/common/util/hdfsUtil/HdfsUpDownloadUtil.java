package xyz.spc.common.util.hdfsUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * HDFS上传下载工具类
 */
@Slf4j
@Service
public final class HdfsUpDownloadUtil {


    /**
     * hdfs的下载
     */
    public static void downFile(String frompath, String aimpath) throws IOException {
        // 1 获取文件系统
        FileSystem fs = HdfsContext.getFileSystem();
        // 2 执行下载操作
        //boolean delSrc 指是否将原文件删除
        //Path src 指要下载的文件路径
        //Path dst 指将文件下载到的路径
        //boolean useRawLocalFileSystem 是否开启文件校验
        fs.copyToLocalFile(false, new Path(frompath), new Path(aimpath), true);
        //3 关闭资源
        fs.close();
        System.out.println("over");
    }

    /**
     * File对象上传到hdfs
     */
//    public static String createFile(MultipartFile mfile, String hdfsPath) throws IOException {
//        InputStream in = null;
//        RowkeyUtil rowkeyUtil = new RowkeyUtil();
//        //用rowkey作为唯一表示
//        String fileName = mfile.getOriginalFilename();
//        fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
//        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
//        System.out.println(suffix);
//        String localPath = "D:\\uploadtemp\\" + rowkeyUtil.getRowkey() + '.' + suffix;
//        mfile.transferTo(new File(localPath));//将文件传入服务器
//        HdfsUpDownloadUtil.searchDir(hdfsPath);
//        String aimpath = hdfsPath + "\\" + rowkeyUtil.getRowkey() + '.' + suffix;
//        FileSystem fs = HdfsContext.getFileSystem();
//        fs.copyFromLocalFile(new Path(localPath), new Path(aimpath));
//        //3 关闭
//        System.out.println("over");
//        System.out.println(localPath);
//        File del = new File(localPath);//将本地文件删除
//        del.delete();
//        return aimpath;
//    }

}
