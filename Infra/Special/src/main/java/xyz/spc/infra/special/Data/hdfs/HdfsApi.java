package xyz.spc.infra.special.Data.hdfs;


import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Trash;
import org.apache.hadoop.fs.TrashPolicy;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;

/**
 * HDFS Java API fs文件系统API类
 *
 * @author yukun24@126.com
 * @blob http://blog.csdn.net/appleyk
 * @date 2018年7月3日15:43:16
 */
public class HdfsApi {


    /**
     * 删除文件或者文件目录
     *
     * @param path
     * @param recursive 是否递归删除：如果path是目录，则该参数设置为true，否则会抛出异常
     * @param skiptrash 是否删除的时候，将文件或者目录放入回收站
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean rmdir(final String path, boolean recursive, boolean skiptrash) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() {
                try {
                    Path dPath;
                    String destPath = "";
                    if (StringUtils.isNotBlank(uri)) {
                        destPath = uri + "/" + path;
                        dPath = new Path(destPath);
                    } else {
                        destPath = path;
                        dPath = new Path(path);
                    }

                    // 如果不跳过回收站，则将删除的对象放入回收站
                    if (!skiptrash) {
                        moveToTrash(destPath);
                        return true;
                    } else {
                        // 是否删除文件目录的时候，采用递归删除文件
                        return fs.delete(dPath, recursive);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getClass() + "," + e.getMessage());
                } catch (IOException e) {
                    System.err.println(e.getClass() + "," + e.getMessage());
                } catch (InterruptedException e) {
                    System.err.println(e.getClass() + "," + e.getMessage());
                }
                return false;
            }
        });
    }


    /**
     * 检查Hadoop的回收站功能是否可用（开启）
     *
     * @return 返回true，证明回收站功能可用
     * @throws Exception
     */
    public boolean trashEnabled() throws Exception {
        Trash trash = new Trash(fs, conf);
        return trash.isEnabled();
    }

    /**
     * 拿到回收站的Path
     *
     * @return 回收站目录 == Path路径对象
     * @throws Exception
     */
    public Path getTrashDir() throws Exception {

        TrashPolicy trashPolicy = TrashPolicy.getInstance(conf, fs, fs.getHomeDirectory());
        return trashPolicy.getCurrentTrashDir().getParent();
    }

    /**
     * 拿到回收站的目录路径
     *
     * @return 回收站目录 字符串形式的路径
     */
    public String getTrashDirPath() throws Exception {
        Path trashDir = getTrashDir();
        // 返回回收站目录URI的原始路径组件 == 字符串形式的路径
        return trashDir.toUri().getRawPath();

    }


    /**
     * 拿到回收站里面指定的文件的路径
     *
     * @param filePath : 回收站里面的文件
     * @return 回收站下面的文件
     * @throws Exception
     */
    public String getTrashDirPath(final String filePath) throws Exception {
        String trashDirPath = getTrashDirPath();
        Path path = new Path(filePath);
        trashDirPath = trashDirPath + "/" + path.getName();
        return trashDirPath;
    }


    /**
     * 将文件或目录放到回收站
     */
    public boolean moveToTrash(final String path) throws IOException, InterruptedException {
        Trash trash = new Trash(fs, conf);
        boolean flag = trash.moveToTrash(new Path(path));
        return flag;

    }

    /**
     * 从回收站恢复指定文件或目录到指定位置
     *
     * @param
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean restoreFromTrash(final String srcPath, final String destPath) throws IOException, InterruptedException {

        /**
         * 把源文件从回收站里面移除来
         */
        move(srcPath, destPath);
        return true;

    }

    /**
     * 清空回收站
     *
     * @return
     * @throws Exception
     */
    public boolean emptyTrash() throws Exception {
        // 第一种方法：使用递归删除目录，暴力清空
        // rmdir(getTrashDirPath(), true, true);

        // 第二种方法：使用expunge方法，删除掉旧的检查点
        Trash tr = new Trash(fs, conf);
        tr.expunge();
        return true;

    }


}
