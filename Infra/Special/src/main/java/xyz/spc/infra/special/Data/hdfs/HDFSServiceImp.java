package xyz.spc.infra.special.Data.hdfs;

import com.bishe.netdisc.entity.UserFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

/**
 * @author third_e
 * @create 2020/4/30 0030-下午 5:12
 */
@Component
public class HDFSServiceImp {
    Logger logger = LoggerFactory.getLogger(HDFSServiceImp.class);

    @Value("${hdfs.path}")
    private String path;
    @Value("${hdfs.user}")
    private String user;
    private Configuration conf;
    private FileSystem fss;

    /**
     * 获取HDFS配置信息
     */
    public Configuration getHDFSConfig() {
        //设置远程登录的用户（即服务器上操作hadoop的用户），默认是当前机器的用户，如果不设置会报拒绝登录的错误
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hadoop3");
        Configuration configuration = new Configuration();
//        // 全分布使用需要配置一下信息
//        configuration.set("fs.defaultFS","hdfs://ns1");//nameservices地址
//        configuration.set("dfs.nameservices", "ns1");
//        configuration.set("dfs.ha.namenodes.ns1", "nn1,nn2");
//        configuration.set("dfs.namenode.rpc-address.ns1.nn1", "192.168.88.131:9000");
//        configuration.set("dfs.namenode.rpc-address.ns1.nn2", "192.168.88.132:9000");
//        configuration.set("hadoop.job.ugi", "hadoop3");
//        configuration.set("dfs.client.failover.proxy.provider.ns1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        try {
            ugi.doAs((PrivilegedExceptionAction) () -> {
                try {
                    //操作HA集群有两种方式，1.通过conf一个个的设置属性，2.将hdfs的两个配置文件放到resource目录下，new Configuration()的时候会自动读取，这种方法最简单
                    conf = new Configuration();
                    //设置集群别名，而不是具体的地址，避免硬编码，它会自动的选择active节点进行操作
                    conf.set("fs.defaultFS", "hdfs://ns1");
                    conf.set("hadoop.job.ugi", "hadoop3");
                    configuration.set("hadoop.job.ugi", "hadoop3");
                    fss = FileSystem.get(conf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configuration;
    }

    /**
     * 获取FileSystem的FS对象
     */
    public FileSystem getFileSystem() throws Exception {
        //配置NameNode地址
        URI uri = new URI(path);
        //伪分布：指定用户名,获取FileSystem对象
        FileSystem fs = FileSystem.get(uri, getHDFSConfig(), user);
        return fs;
    }

    /**
     * 列出子目录和文件
     *
     * @param dir
     * @return
     * @throws Exception
     */
    @Override
    public List<Integer> ls(String dir) throws Exception {
        logger.info("-------------->HDFS ls start");
        System.out.println(dir);
        FileSystem fs = getFileSystem();
        Path path = new Path(dir);
        //如果不存在，返回
        if (!fs.exists(path)) {
            logger.error("dir:" + dir + " not exists!");
            throw new RuntimeException("dir:" + dir + " not exists!");
        }

        List<Integer> list = new ArrayList<>();
        FileStatus[] filesStatus = fs.listStatus(path);
        for (FileStatus f : filesStatus) {
            String num = f.getPath().toUri().getPath().split("/")[3].split("_")[1];
            Integer chunkNum = Integer.parseInt(num);
            list.add(chunkNum);
        }
        //不需要再操作FileSystem了，关闭
        fs.close();


        return list;
    }

    @Override
    public FileStatus[] tempFile(String dir) throws Exception {
        logger.info("-------------->HDFS tempFile start");
        FileSystem fs = getFileSystem();
        Path path = new Path(dir);
        //如果不存在，返回
        if (!fs.exists(path)) {
            logger.error("dir:" + dir + " not exists!");
            throw new RuntimeException("dir:" + dir + " not exists!");
        }

        List<UserFile> list = new ArrayList<UserFile>();
        FileStatus[] filesStatus = fs.listStatus(path);
        List<FileStatus> fileStatuses = new ArrayList<>();
//        int sort =0;
//        for (FileStatus files: filesStatus){
//            FileStatus fileStatus = files;
//            int num = Integer.parseInt(fileStatus.getPath().toUri().getPath().split("_")[1]);
//            if (sort == num) {
//                fileStatuses.add(files);
//                sort++;
//            }
//        }
        return filesStatus;
    }

    /**
     * 校验文件的md5
     *
     * @param dir
     * @return
     * @throws Exception
     */
    @Override
    public String chechMD5(String dir) throws Exception {
        logger.info("-------------->HDFS chechMD5 start");
        FileSystem fs = getFileSystem();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        FSDataInputStream in = null;
        Path dstPath = new Path(dir);
        in = fs.open(dstPath);
        byte[] buffer = new byte[2097152];
        int c;
        while ((c = in.read(buffer)) != -1) {
            md5.update(buffer, 0, c);
        }
        BigInteger bi = new BigInteger(1, md5.digest());
        String value = bi.toString(16);
        in.close();
        logger.info("-------------->HDFS chechMD5 end");
        return value;
    }

    /**
     * 合并文件
     *
     * @param fileStatuses
     * @throws Exception
     */
    @Override
    public void mergeFile(FileStatus[] fileStatuses, String dstHDFSFile) throws Exception {
        logger.info("-------------->HDFS mergeFile start");

        FSDataOutputStream out = null;
        FSDataInputStream in = null;
        FileSystem fs = getFileSystem();
        FileSystem local = getFileSystem();
        Path dstPath = new Path(dstHDFSFile);
        // 打开输出流
        out = fs.create(dstPath);
        for (FileStatus fileStatus : fileStatuses) {
            Path filePath = fileStatus.getPath();
            in = local.open(filePath);
            IOUtils.copyBytes(in, out, 2097152, false); // 复制数据
            in.close();
        }
        if (out != null) {
            // 关闭输出流
            out.close();
        }
        logger.info("-------------->HDFS mergeFile end");
    }

    /**
     * 创建目录
     *
     * @param dir 目录地址
     * @throws Exception
     */
    @Override
    public void mkdir(String dir) throws Exception {
        logger.info("-------------->HDFS mkdir start");
        FileSystem fs = getFileSystem();
        // 目录不存在则创建
        if (!fs.exists(new Path(dir))) {
            fs.mkdirs(new Path(dir));
        }
        //不需要再操作FileSystem了，关闭client
        fs.close();
        logger.info("-------------->HDFS mkdir " + dir + " successful");
    }

    /**
     * 判断文件是否存在
     *
     * @param dir
     * @return
     * @throws Exception
     */
    @Override
    public Boolean exitFile(String dir) throws Exception {
        logger.info("-------------->HDFS exitFile start");
        FileSystem fs = getFileSystem();
        if (fs.exists(new Path(dir))) {
            fs.close();
            logger.info("-------------->HDFS exitFile end");
            return true;
        } else {
            fs.close();
            logger.info("-------------->HDFS exitFile end");
            return false;
        }

    }

    /**
     * 删除目录或文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public String rm(String path) throws Exception {
        FileSystem fs = getFileSystem();
        Path filePath = new Path(path);
        fs.delete(filePath, true);
        //不需要再操作FileSystem了，关闭client
        fs.close();
        return null;
    }

    /**
     * 上传文件
     *
     * @param is          输入流
     * @param dstHDFSFile 目标目录
     * @throws Exception
     */
    @Override
    public void upload(InputStream is, String dstHDFSFile) throws Exception {
        logger.info("-------------->HDFS upload start");
        FileSystem fs = getFileSystem();
        Path dstPath = new Path(dstHDFSFile);
        FSDataOutputStream os = fs.create(dstPath);
        IOUtils.copyBytes(is, os, 1024, true);
    }

    /**
     * 下载文件或目录
     *
     * @param file
     * @param os
     * @throws Exception
     */
    @Override
    public void download(String file, OutputStream os) throws Exception {
        logger.info("-------------->HDFS download start");
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(file);
        FSDataInputStream is = fs.open(srcPath);
        IOUtils.copyBytes(is, os, 1024, true);
    }

    /**
     * 移动到
     */
    @Override
    public void mv() {

    }

    @Override
    public String[] rename(String path, String dirName) throws Exception {
        return new String[0];
    }

    @Override
    public String getFileName(String path) {
        return null;
    }

    @Override
    public List getOptionTranPath(String path) {
        return null;
    }

    @Override
    public List<UserFile> searchFileByPage(String keyWord, int pageSize, int pageNum) throws Exception {
        return null;
    }

    @Override
    public List getStaticNums() {
        return null;
    }
}
