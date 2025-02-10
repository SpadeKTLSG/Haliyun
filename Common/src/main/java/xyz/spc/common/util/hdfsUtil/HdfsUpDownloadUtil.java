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


    private static final FileSystem dfs = HdfsContext.getFileSystem();

    /**
     * 下载HDFS文件到本地
     *
     * @param fromPath   hdfs文件路径
     *                   例如：/user/hadoop/word.txt
     * @param targetPath 本地文件路径
     *                   例如：D:/word.txt
     */
    public static void downFile(String fromPath, String targetPath) throws IOException {

        //参数:
        //boolean delSrc 指是否将原文件删除
        //boolean useRawLocalFileSystem 是否开启文件校验
        dfs.copyToLocalFile(false, new Path(fromPath), new Path(targetPath), true);
    }


}
