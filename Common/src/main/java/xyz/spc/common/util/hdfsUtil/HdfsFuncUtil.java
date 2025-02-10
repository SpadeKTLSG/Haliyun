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
     * 尝试创建目录
     */
    public static void tryMkdir(String stringDir) throws IOException {
        Path dir = new Path(stringDir);
        //查找目录
        if (!dfs.exists(dir)) {
            log.debug("目录不存在: 开始创建目录 ");
            dfs.mkdirs(dir);
            log.debug("目录创建成功 ");
        } else {
            log.debug("目录已存在 ");
        }
    }

}
