package xyz.spc.common.util.hdfsUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.io.IOException;


/**
 * HDFS功能工具类 (Add/Update/Delete)
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

    /**
     * 删除文件/文件夹, 递归删除
     */
    public static void deleteF(String stringDir) throws Exception {
        dfs.delete(new Path(stringDir), true);
    }

    /**
     * 创建文件, 返回文件的输出字节流. 可以使用write进行内容添加
     *
     * @param path      path
     * @param overwrite 覆盖存在的文件
     * @return output stream
     */
    public static FSDataOutputStream createFile(final String path, final boolean overwrite) throws IOException {
        return dfs.create(new Path(path), overwrite);
    }


    /**
     * 移动文件或目录
     */
    public static boolean move(final String src, final String dest) throws Exception {

        /*
         * 是否删除源文件 == true
         * 删除源文件 copy原理: 1.先复制字节 2.然后递归删除源文件或目录
         */
        if (!HdfsQueryUtil.checkFileExist(src)) {
            log.warn(src + ": HDFS文件不存在，本次操作终止");
            return false;
        }
        return FileUtil.copy(dfs, new Path(src), dfs, new Path(dest), true, dfs.getConf());
    }

    /**
     * 复制文件或目录
     */
    public static boolean copy(final String src, final String dest) throws Exception {
        return FileUtil.copy(dfs, new Path(src), dfs, new Path(dest), false, dfs.getConf());
    }

    /**
     * 目录/文件重命名
     */
    public static boolean rename(final String src, final String dest) throws Exception {
        return dfs.rename(new Path(src), new Path(dest));
    }
}
