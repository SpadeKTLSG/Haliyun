package xyz.spc.common.util.hdfsUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * HDFS上传下载工具类 (IO)
 */
@Slf4j
public final class HdfsUpDownloadUtil {


    private static final FileSystem dfs = HdfsContext.getFileSystem();

    /**
     * 下载HDFS文件到本地JVM机器磁盘
     *
     * @param fromPath   hdfs文件路径
     *                   例如：/user/hadoop/word.txt
     * @param targetPath 本地文件路径
     *                   例如：D:/word.txt
     */
    public static void down2Disk(String fromPath, String targetPath) throws IOException {

        //参数:
        //boolean delSrc 指是否将原文件删除
        //boolean useRawLocalFileSystem 是否开启文件校验
        dfs.copyToLocalFile(false, new Path(fromPath), new Path(targetPath), true);
    }


    /**
     * 上传文件: InputStream模式
     */
    public static void upByIS(String taget, InputStream is) throws Exception {
        FSDataOutputStream os = dfs.create(new Path(taget));
        IOUtils.copyBytes(is, os, 1024, true);
    }

    /**
     * 下载文件: OutputStream模式
     */
    public static void downByOS(String target, OutputStream os) throws Exception {
        FSDataInputStream is = dfs.open(new Path(target));
        IOUtils.copyBytes(is, os, 1024, true);
    }
}
