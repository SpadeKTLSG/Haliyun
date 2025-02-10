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


}
