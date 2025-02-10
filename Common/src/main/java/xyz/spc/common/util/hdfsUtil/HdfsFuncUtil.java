package xyz.spc.common.util.hdfsUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.*;

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


    /**
     * 删除文件或者文件目录
     *
     * @param recursive 是否递归删除：如果path是目录，则该参数设置为true，否则会抛出异常
     * @param skiptrash 是否删除的时候，将文件或者目录放入回收站
     */
    public static boolean rmdir(final String path, boolean recursive, boolean skiptrash) throws IOException, InterruptedException {

        // 不跳过回收站则将删除的对象放入回收站
        if (!skiptrash) {
            move2Trash(path);
            return true;
        }

        // 采用递归删除文件
        return dfs.delete(new Path(path), recursive);
    }


    /**
     * 检查Hadoop的回收站功能是否可用（我默认关闭, 采用自己文件夹实现）
     *
     * @return 返回true，证明回收站功能可用
     */
    @Deprecated
    public static boolean trashEnabled() throws Exception {
        Trash trash = new Trash(dfs, dfs.getConf());
        return trash.isEnabled();
    }

    /**
     * 拿到回收站的Path
     *
     * @return 回收站目录 == Path路径对象
     */
    @Deprecated
    public static Path getTrashDir() throws Exception {
        TrashPolicy trashPolicy = TrashPolicy.getInstance(dfs.getConf(), dfs, dfs.getHomeDirectory());
        return trashPolicy.getCurrentTrashDir().getParent();
    }

    /**
     * 拿到回收站的目录路径
     *
     * @return 回收站目录 字符串形式的路径
     */
    @Deprecated
    public static String getTrashDirPath() throws Exception {
        Path trashDir = getTrashDir();
        // 返回回收站目录URI的原始路径组件 -> 字符串形式的路径
        return trashDir.toUri().getRawPath();
    }


    /**
     * 拿到回收站里面指定的文件的路径
     *
     * @param filePath : 回收站里面的文件
     * @return 回收站下面的文件
     */
    @Deprecated
    public static String getTrashDirPath(final String filePath) throws Exception {
        String trashDirPath = getTrashDirPath();
        Path path = new Path(filePath);
        trashDirPath = trashDirPath + "/" + path.getName();
        return trashDirPath;
    }


    /**
     * 将文件或目录放到回收站
     */
    @Deprecated
    public static boolean move2Trash(final String path) throws IOException, InterruptedException {
        Trash trash = new Trash(dfs, dfs.getConf());
        return trash.moveToTrash(new Path(path));

    }

    /**
     * 从回收站恢复指定文件或目录到指定位置
     */
    @Deprecated
    public static boolean restoreFrTrash(final String srcPath, final String destPath) throws Exception {
        return move(srcPath, destPath);
    }

    /**
     * 清空回收站 (也可暴力)
     */
    @Deprecated
    public static boolean emptyTrash() throws Exception {
        Trash tr = new Trash(dfs, dfs.getConf());
        tr.expunge();
        return true;
    }


}
