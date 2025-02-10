package xyz.spc.common.util.hdfsUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.PrivilegedExceptionAction;

/**
 * HDFS上传下载工具类 (IO)
 */
@Slf4j
public final class HdfsIOUtil {


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

    /**
     * 打开文件: FSDataInputStream模式
     * note: FSDataInputStream继承DataInputStream; 并实现了Seekable等接口
     */
    public FSDataInputStream open(final String path) throws IOException {
        return dfs.open(new Path(path));
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


}
