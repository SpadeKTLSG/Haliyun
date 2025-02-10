package xyz.spc.infra.special.Data.hdfs;


import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.security.UserGroupInformation;
import xyz.spc.test.pojo.HdfsApiException;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HDFS Java API fs文件系统API类
 *
 * @author yukun24@126.com
 * @blob http://blog.csdn.net/appleyk
 * @date 2018年7月3日15:43:16
 */
public class HdfsApi {

    // Hadoop的用户和组信息。
    private final UserGroupInformation ugi;
    // 创建配置时候加载core-site.xml、hdfs-site.xml
    // 如果本地有，加载本地
    // 否则加载远程HDFS文件系统上的配置文件，远程必须指定uri


    private final Configuration conf;

    /**
     * 如果不指定hdfs文件系统的uri，默认文件系统指向本地环境，比如当前环境是Windows 如果指定hdfs文件系统的uri
     * ，也就是连接远程Hadoop文件系统，
     * 请确保uri和core-site.xml里面配置的fs.defaultFS的value值一样，否者获取远程文件系统失败
     */
    private String uri;
    /**
     * 在Hadoop中,FileSystem是一个通用的文件系统API FileSystem是一个抽象类
     */
    private FileSystem fs;


    /**
     * 获取HDFS文件系统的状态（存储情况）
     *
     * @return fs的状态
     * @throws Exception
     */
    public synchronized FsStatus getStatus() throws Exception {
        return execute(new PrivilegedExceptionAction<FsStatus>() {
            public FsStatus run() throws IOException {
                FsStatus status = fs.getStatus();
                System.out.println("容量：" + getByteToSize(status.getCapacity()));
                System.out.println("已用：" + getByteToSize(status.getUsed()));
                System.out.println("剩余：" + getByteToSize(status.getRemaining()));
                return status;
            }
        });
    }

    /**
     * 创建文件目录/文件
     *
     * @param path
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean mkdir(final String path) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() throws IllegalArgumentException, IOException {
                Path dPath = new Path(uri + "/" + path);
                return fs.mkdirs(dPath);
            }
        });

    }

    /**
     * 创建文件 == 返回文件的输出字节流，可以使用write进行内容添加
     *
     * @param path      path
     * @param overwrite 覆盖存在的文件
     * @return output stream
     * @throws IOException
     * @throws InterruptedException
     */
    public FSDataOutputStream createFile(final String path, final boolean overwrite) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<FSDataOutputStream>() {
            public FSDataOutputStream run() throws Exception {
                return fs.create(new Path(uri + "/" + path), overwrite);
            }
        });
    }


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
     * 文件上传至 HDFS
     *
     * @param delSrc    == 是否删除源文件
     * @param overwrite == 如果目标文件已经存在，是否覆盖目标文件
     * @param srcFile   == 源文件路径
     * @param destPath  == 目标文件所在的目录路径
     */
    public void upLoadFile(final String srcFile, final String destPath, boolean delSrc, boolean overwrite) throws IOException, InterruptedException {
        execute(new PrivilegedExceptionAction<Void>() {
            public Void run() throws IOException, InterruptedException {

                // 源文件路径
                Path srcPath = new Path(srcFile);
                // 目标文件要存放的目录如果不存在，则创建
                existDir(destPath, true);
                // 目标文件Path
                Path dPath;
                if (StringUtils.isNotBlank(uri)) {
                    dPath = new Path(uri + "/" + destPath);
                } else {
                    // 否者 默认上传到根目录下
                    dPath = new Path(uri + "/");
                }

                // 实现文件上传
                try {
                    fs.copyFromLocalFile(delSrc, overwrite, srcPath, dPath);
                    System.out.println("文件：" + srcPath + ",上传成功！");
                } catch (IOException e) {
                    System.err.println(e.getClass() + "," + e.getMessage());
                }
                return null;
            }
        });

    }

    /**
     * 按字节从客户端拉取字节读写到服务端
     *
     * @param destPath
     * @throws IOException
     * @throws InterruptedException
     */
    public void upLoadFile(InputStream in, final String destPath) throws IOException, InterruptedException {

        execute(new PrivilegedExceptionAction<Void>() {
            public Void run() throws IOException, InterruptedException {
                // 目标文件Path
                Path dPath;
                if (StringUtils.isNotBlank(uri)) {
                    dPath = new Path(uri + "/" + destPath);
                } else {
                    // 否者 默认上传到根目录下
                    dPath = new Path(uri + "/");
                }

                OutputStream os = fs.create(dPath);
                /**
                 * in ：输入字节流（从要上传的文件中读取）
                 * out：输出字节流（字节输出到目标文件）
                 * 2048：每次写入2048
                 * true：不管成功与否，最后都关闭stream资源
                 */
                org.apache.hadoop.io.IOUtils.copyBytes(in, os, 2048, true);
                return null;
            }
        });
    }


    /**
     * 从 HDFS文件系统上 下载文件到指定destPath路径下
     *
     * @param srcFile
     * @param destPath
     * @throws InterruptedException
     * @throws IOException
     */
    public void downLoadFile(final String srcFile, final String destPath) throws IOException, InterruptedException {

        execute(new PrivilegedExceptionAction<Void>() {
            public Void run() {
                // 源路径
                Path sPath;
                if (StringUtils.isNotBlank(uri)) {
                    sPath = new Path(uri + "/" + srcFile);
                } else {
                    sPath = new Path(srcFile);
                }

                /**
                 * 本地路径或者Linux下路径
                 */
                Path dstPath = new Path(destPath);
                try {
                    fs.copyToLocalFile(sPath, dstPath);
                    System.out.println("文件下载至：" + destPath + sPath.getName());
                } catch (IOException e) {
                    System.err.println(e);
                }
                return null;
            }
        });
    }

    /**
     * 从 HDFS文件系统上 读取文件流写入到本地文件
     *
     * @param srcFile
     * @param
     * @throws InterruptedException
     * @throws IOException
     */
    public void downLoadFile(final String srcFile, HttpServletResponse response, boolean flag) throws IOException, InterruptedException {

        execute(new PrivilegedExceptionAction<Void>() {
            public Void run() throws UnsupportedEncodingException {
                // 源路径
                Path sPath;
                if (StringUtils.isNotBlank(uri)) {
                    sPath = new Path(uri + "/" + srcFile);
                } else {
                    sPath = new Path(srcFile);
                }


                String fileName = srcFile.substring(srcFile.lastIndexOf("/") + 1);
                System.err.println(fileName);
                response.setContentType(new MimetypesFileTypeMap().getContentType(new File(fileName)));
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

                try {
                    InputStream is = fs.open(sPath);
                    byte[] data = new byte[1024];
                    OutputStream out = response.getOutputStream();

                    while (is.read(data) != -1) {
                        out.write(data);
                    }
                    out.flush();
                    is.close();
                    out.close();

                } catch (IOException e) {
                    System.err.println(e);
                }
                return null;
            }
        });
    }

    /**
     * 查找某个文件在 HDFS集群的位置【文件块的信息】
     *
     * @param filePath
     * @return BlockLocation[]
     */
    public BlockLocation[] getFileBlockLocations(final String filePath) {

        Path path;
        if (StringUtils.isNotBlank(uri)) {
            path = new Path(uri + "/" + filePath);
        } else {
            path = new Path(filePath);
        }
        // 文件块位置列表
        BlockLocation[] blkLocations = new BlockLocation[0];
        try {
            // 获取文件目录
            FileStatus filestatus = fs.getFileStatus(path);
            // 获取文件块位置列表
            blkLocations = fs.getFileBlockLocations(filestatus, 0, filestatus.getLen());
            for (BlockLocation blockLocation : blkLocations) {
                long length = blockLocation.getLength();
                System.out.println("文件块的长度[" + length + "/1024 = 文件的大小" + (length / 1024d) + "kb]：" + length);
                System.out.println("文件块的偏移量：" + blockLocation.getOffset());
                List<String> nodes = new ArrayList<>();
                Collections.addAll(nodes, blockLocation.getHosts());
                System.out.println("文件块存储的主机（DataNode）列表（主机名）：" + nodes);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return blkLocations;
    }

    /**
     * 目录 、文件重命名
     *
     * @param srcPath 源路径
     * @param dstPath 目标路径
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean rename(final String srcPath, final String dstPath) throws Exception {
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() throws HdfsApiException {
                boolean flag = false;
                try {

                    Path sPath;
                    Path dPath;

                    if (StringUtils.isNotBlank(uri)) {
                        sPath = new Path(uri + "/" + srcPath);
                        dPath = new Path(uri + "/" + dstPath);
                    } else {
                        sPath = new Path(srcPath);
                        dPath = new Path(dstPath);
                    }

                    if (sPath.getName().equals(dPath.getName())) {
                        flag = true;
                    } else {
                        flag = fs.rename(sPath, dPath);
                    }

                    System.out.println(srcPath + " rename to " + dstPath + ",成功");
                } catch (IOException e) {
                    System.err.println(srcPath + " rename to " + dstPath + " error: " + e.getMessage());
                }

                return flag;
            }
        });

    }

    /**
     * 判断路径Path是否存在
     *
     * @param srcPath
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean exists(final String srcPath) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<Boolean>() {

            @Override
            public Boolean run() throws Exception {

                Path sPath;
                if (StringUtils.isNotBlank(uri)) {
                    sPath = new Path(uri + "/" + srcPath);
                } else {
                    sPath = new Path(srcPath);
                }
                return fs.exists(sPath);
            }

        });
    }

    /**
     * 判断目录是否存在
     *
     * @param dirPath
     * @param create  当目录不存在的时候，是否创建目录，true：创建，false：不创建直接返回
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean existDir(final String dirPath, boolean create) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() {
                boolean flag = false;
                Path dPath;
                if (StringUtils.isEmpty(dirPath)) {
                    return flag;
                } else {
                    dPath = new Path(uri + "/" + dirPath);
                }
                try {
                    if (create) {
                        if (!fs.exists(dPath)) {
                            fs.mkdirs(dPath);
                        }
                    }
                    // 如果是目录，返回true
                    if (fs.isDirectory(dPath)) {
                        flag = true;
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
                return flag;
            }
        });

    }

    /**
     * 打开文件 FSDataInputStream继承DataInputStream，并实现了Seekable等接口
     *
     * @param path path
     * @return input stream
     * @throws IOException
     * @throws InterruptedException
     */
    public FSDataInputStream open(final String path) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<FSDataInputStream>() {
            public FSDataInputStream run() throws Exception {
                return fs.open(new Path(uri + "/" + path));
            }
        });
    }


    /**
     * 复制文件从src目录到dest目录
     *
     * @param src  源path
     * @param dest 目标path
     */
    public void copy(final String src, final String dest) throws Exception {

        boolean result = execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() throws Exception {
                // 是否删除源文件 == false，不删除源文件
                return FileUtil.copy(fs, new Path(uri + "/" + src), fs, new Path(uri + "/" + dest), false, conf);
            }
        });

        if (!result) {
            throw new Exception("HDFS010 Can't copy source file from \" + src + \" to \" + dest");
        }
    }

    /**
     * 移动文件或者目录
     *
     * @param src
     * @param dest
     * @throws Exception
     */
    public void move(final String src, final String dest) throws Exception {
        boolean result = execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() throws Exception {
                /**
                 * 是否删除源文件 == true，删除源文件 copy原理: 1.先复制字节 2.然后递归删除源文件或目录
                 */
                Path sPath;
                Path dPath;
                String srcPath;
                if (StringUtils.isNotBlank(uri)) {
                    srcPath = uri + "/" + src;
                    sPath = new Path(srcPath);
                    dPath = new Path(uri + "/" + dest);
                } else {
                    srcPath = src;
                    sPath = new Path(srcPath);
                    dPath = new Path(dest);
                }
                if (!existFile(src)) {
                    System.out.println(srcPath + " == 不存在，本次操作终止");
                    return false;
                }
                boolean copy = FileUtil.copy(fs, sPath, fs, dPath, true, conf);
                return copy;
            }
        });

        if (!result) {
            throw new Exception("HDFS010 Can't copy source file from \" + src + \" to \" + dest");
        }

    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean existFile(final String filePath) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() {
                boolean flag = false;
                if (StringUtils.isEmpty(filePath)) {
                    return flag;
                }
                try {
                    Path path;
                    if (StringUtils.isNotBlank(uri)) {
                        path = new Path(uri + "/" + filePath);
                    } else {
                        path = new Path(filePath);
                    }
                    // 如果文件存在，返回true
                    if (fs.exists(path)) {
                        flag = true;
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
                return flag;
            }
        });

    }

    /**
     * 拿到HDFS的home目录 == /user/用户/
     *
     * @return home directory
     * @throws Exception
     */
    public Path getHomeDir() throws Exception {
        return execute(new PrivilegedExceptionAction<Path>() {
            public Path run() throws IOException {
                return fs.getHomeDirectory();
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
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() throws IOException {
                Trash trash = new Trash(fs, conf);
                return trash.isEnabled();
            }
        });
    }

    /**
     * 拿到回收站的Path
     *
     * @return 回收站目录 == Path路径对象
     * @throws Exception
     */
    public Path getTrashDir() throws Exception {
        return execute(new PrivilegedExceptionAction<Path>() {
            public Path run() throws IOException {
                TrashPolicy trashPolicy = TrashPolicy.getInstance(conf, fs, fs.getHomeDirectory());
                return trashPolicy.getCurrentTrashDir().getParent();
            }
        });
    }

    /**
     * 拿到回收站的目录路径
     *
     * @return 回收站目录 == 字符串形式的路径
     * @throws Exception
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
     * 将文件或目录放到回收站 == 用户删除文件或目录的时候用
     *
     * @param path path
     * @return success
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean moveToTrash(final String path) throws IOException, InterruptedException {
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() throws IOException, InterruptedException {
                /**
                 * 注意这里有个"bug"，如果使用方法Trash.moveToAppropriateTrash(fs, new
                 * Path(path), conf);
                 * 会报异常，跟进去发下，fs传进去后，只是做了陪衬，在其方法内部会重新拿到FileSystem
                 * 因此，这里会造成用户的丢失，比如，最开始设置的root用户，进去后就成了Administrator用户
                 */

                Trash trash = new Trash(fs, conf);
                boolean flag = trash.moveToTrash(new Path(path));
                return flag;
            }
        });
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
        return execute(new PrivilegedExceptionAction<Boolean>() {
            @Override
            public Boolean run() throws Exception {
                /**
                 * 把源文件从回收站里面移除来
                 */
                move(srcPath, destPath);
                return true;
            }

        });

    }

    /**
     * 清空回收站
     *
     * @return
     * @throws Exception
     */
    public boolean emptyTrash() throws Exception {
        return execute(new PrivilegedExceptionAction<Boolean>() {
            public Boolean run() throws Exception {

                // 第一种方法：使用递归删除目录，暴力清空
                // rmdir(getTrashDirPath(), true, true);

                // 第二种方法：使用expunge方法，删除掉旧的检查点
                Trash tr = new Trash(fs, conf);
                tr.expunge();
                return true;
            }
        });
    }

    /**
     * 往文件里面写（String）内容 == 如果文件存在则覆盖源文件，适用于创建文本文件并写入内容
     *
     * @param filePath
     * @param content
     * @throws IOException
     * @throws InterruptedException
     */
    public void putStringToFile(final String filePath, final String content) throws HdfsApiException {
        try {
            execute(new PrivilegedExceptionAction<Void>() {
                public Void run() throws IOException, InterruptedException {
                    // 创建一个文件，并拿到文件的FS数据输出流，便于写入字节
                    final FSDataOutputStream stream = createFile(filePath, true);
                    stream.write(content.getBytes());
                    stream.close();
                    return null;
                }
            }, true);
        } catch (IOException e) {
            throw new HdfsApiException("HDFS020 Could not write file " + filePath, e);
        } catch (InterruptedException e) {
            throw new HdfsApiException("HDFS021 Could not write file " + filePath, e);
        }
    }

    /**
     * 往文件里面写（String）内容 == 如果文件存在，内容追加到文件的末尾
     *
     * @param filePath
     * @param content
     * @throws HdfsApiException
     */
    public void appendStringToFile(final String filePath, final String content) throws HdfsApiException {
        try {
            execute(new PrivilegedExceptionAction<Void>() {
                public Void run() throws IOException, InterruptedException {
                    // 创建一个文件，并拿到文件的FS数据输出流，便于写入字节
                    final FSDataOutputStream stream = appendFile(filePath);
                    stream.write(content.getBytes());
                    stream.close();
                    return null;
                }
            }, true);
        } catch (IOException e) {
            throw new HdfsApiException("HDFS020 Could not append file " + filePath, e);
        } catch (InterruptedException e) {
            throw new HdfsApiException("HDFS021 Could not append file " + filePath, e);
        }
    }

    /**
     * 读取文件，并将文件的内容以字符串的形式返回
     *
     * @param filePath path to file
     * @throws InterruptedException
     */
    public String readFileToString(final String filePath) throws HdfsApiException {
        FSDataInputStream stream;
        try {
            // 打开一个文件，获得FS数据输入流，便于读取输出
            stream = open(filePath);
            return IOUtils.toString(stream);
        } catch (IOException e) {
            throw new HdfsApiException("HDFS060 Could not read file " + filePath, e);
        } catch (InterruptedException e) {
            throw new HdfsApiException("HDFS061 Could not read file " + filePath, e);
        }

    }

    /**
     * 在HDFS上，使用ugi的doAs执行action，记录异常
     *
     * @param action 策略对象
     * @param <T>    run方法里面返回值的类型
     * @return 出现异常情况。每个实现 PrivilegedExceptionAction 的类都应该记录其 run 方法能够抛出的异常。
     * @throws IOException
     * @throws InterruptedException
     */
    public <T> T execute(PrivilegedExceptionAction<T> action) throws IOException, InterruptedException {
        return execute(action, false);
    }

    /**
     * 在HDFS上，使用ugi的doAs执行action，记录异常 方法重载，第二个参数alwaysRetry不设置，默认重试三次异常后，跳出循环
     *
     * @param action 策略对象
     * @param <T>    result type
     * @return result of operation
     * @throws IOException
     * @throws InterruptedException
     */
    public <T> T execute(PrivilegedExceptionAction<T> action, boolean alwaysRetry) throws IOException, InterruptedException {

        T result = null;

        /**
         * 由于HDFS-1058，这里采用了重试策略。HDFS可以随机抛出异常 IOException关于从DN中检索块(如果并发读写)
         * 在特定文件上执行(参见HDFS-1058的详细信息)。
         */
        int tryNumber = 0;
        boolean succeeded = false;
        do {
            tryNumber += 1;
            try {
                // doAs中执行的操作都是以proxyUser用户的身份执行
                result = ugi.doAs(action);
                succeeded = true;
            } catch (IOException ex) {
                if (!Strings.isNullOrEmpty(ex.getMessage()) && !ex.getMessage().contains("无法获取块的长度：")) {
                    throw ex;
                }

                // 尝试超过>=3次，抛出异常，do while 退出
                if (tryNumber >= 3) {
                    throw ex;
                }
                LOG.info("HDFS抛出'IOException:无法获得块长度'的异常. " + "再次尝试... 尝试 #" + (tryNumber + 1));
                LOG.error("再次尝试: " + ex.getMessage(), ex);
                Thread.sleep(1000); // 1s后再试
            }
        } while (!succeeded);
        return result;
    }


}
