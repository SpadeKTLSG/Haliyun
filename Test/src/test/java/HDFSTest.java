import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HDFSTest {



    //在HDFS上创建目录
    @Test
    public void testMkDir() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");
        FileSystem fs = FileSystem.get(conf);
        // 创建目录
        boolean flag = fs.mkdirs(new Path("/inputdata"));
        System.out.println(flag);
    }

    //    通过FileSystemAPI读取数据（下载文件）
    @Test
    public void testDownload() throws Exception {
        // 构造一个输入流 <-------HDFS
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.2.123:9000"), new Configuration());
        InputStream in = fs.open(new Path("/inputdata/a.war"));

        // 构造一个输出流
        OutputStream out = new FileOutputStream("d:\\a.war");
        IOUtils.copy(in, out);
    }

    //写入数据（上传文件）
    @Test
    public void testUpload() throws Exception {
        // 指定上传的文件（输入流）
        InputStream in = new FileInputStream("d:\\test.war");

        // 构造输出流 ----> HDFS
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.2.123:9000"), new Configuration());
        OutputStream out = new FileOutputStream("hdfs://
                // 工具类 ---> 直接实现上传和下载
                IOUtils.copy(in, out);
    }

    //查看目录及文件信息
    @Test
    public void checkFileInformation() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        FileSystem fs = FileSystem.get(conf);
        FileStatus[] status = fs.listStatus(new Path("/hbase"));

        for (FileStatus f : status) {
            String dir = f.isDirectory() ? "目录" : "文件";
            String name = f.getPath().getName();
            String path = f.getPath().toString();
            System.out.println(dir + "------" + name + ",path:" + path);
            System.out.println(f.getAccessTime());
            System.out.println(f.getBlockSize());
            System.out.println(f.getGroup());
            System.out.println(f.getLen());
            System.out.println(f.getModificationTime());
            System.out.println(f.getOwner());
            System.out.println(f.getPermission());
            System.out.println(f.getReplication());
        }

    }

    //查找某个文件在HDFS集群的位置
    @Test
    public void findFileBlockLocation() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        FileSystem fs = FileSystem.get(conf);
        FileStatus fStatus = fs.getFileStatus(new Path("/data/mydata.txt"));

        BlockLocation[] blocks = fs.getFileBlockLocations(fStatus, 0, fStatus.getLen());
        for (BlockLocation block : blocks) {
            System.out.println(Arrays.toString(block.getHosts()) + "\t" + Arrays.toString(block.getNames()));
        }
    }

    //删除数据
    @Test
    public void deleteFile() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        FileSystem fs = FileSystem.get(conf);
        // 第二个参数表示是否递归
        boolean flag = fs.delete(new Path("/mydir/test.txt", false));
        System.out.println(flag ? "删除成功" : "删除失败");
    }

    //    获取HDFS集群上所有数据节点的信息
    @Test
    public void testDataNode() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        DistributedFileSystem fs = (DistributedFileSystem) FileSystem.get(conf);
        DatanodeInfo[] dataNodeStats = fs.getDataNodeStats();
        for (DatanodeInfo dataNode : dataNodeStats) {
            System.out.println(dataNode.getHostName() + "\t" + dataNode.getName());
        }
    }
}
