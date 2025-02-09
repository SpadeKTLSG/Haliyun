package xyz.spc.common.util.hdfsUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;


/**
 * HDFS功能工具类
 */
@Slf4j
public final class HdfsFuncUtil {

    private static final FileSystem dfs = HdfsContext.getFileSystem();

    /**
     * hdfs是否存在文件夹
     */
    public static void searchDir(String stringDir) throws IOException {
        Path dir = new Path(stringDir);
        //查找目录
        if (!dfs.exists(dir)) {
            System.out.println("目录不存在，正在准备创建！");
            dfs.mkdirs(dir);
            System.out.println("创建目录成功！");
        } else {
            System.out.println("目录已存在！");
        }
    }

}
