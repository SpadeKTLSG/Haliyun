package xyz.spc.common.util.hdfsUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * HDFS查询工具类 (Query)
 */
@Slf4j
public class HdfsQueryUtil {

    private static final FileSystem dfs = HdfsContext.getFileSystem();


    /**
     * 拿到HDFS的home目录 => /user/用户/
     */
    public static Path getHomeDir() {
        return dfs.getHomeDirectory();
    }

    /**
     * 查完对应路径文件是否存在
     */
    public static boolean checkFileExist(String pathStr) throws Exception {
        return !dfs.exists(new Path(pathStr));
    }

    /**
     * 查看路径下的文件信息
     */
    public static void getDirInfo(String stringDir) throws Exception {

        FileStatus[] status = dfs.listStatus(new Path(stringDir));

        for (FileStatus f : status) {
            String dir = f.isDirectory() ? "目录" : "文件";
            String name = f.getPath().getName();
            String path = f.getPath().toString();
            log.debug("目录:" + dir + " ==== " + name + ", path:" + path);
            log.debug("访问时间:" + f.getAccessTime());
            log.debug("块大小:" + f.getBlockSize());
            log.debug("所属组:" + f.getGroup());
            log.debug("长度:" + f.getLen());
            log.debug("修改时间:" + f.getModificationTime());
            log.debug("所有者:" + f.getOwner());
            log.debug("权限:" + f.getPermission());
            log.debug("副本数:" + f.getReplication());
            log.debug("===========");
        }

    }


    /**
     * 查找某个文件在HDFS集群的位置 (调试)
     */

    public static void findFileBlockLocation(String pathStr) throws Exception {

        FileStatus status = dfs.getFileStatus(new Path(pathStr));

        BlockLocation[] blocks = dfs.getFileBlockLocations(status, 0, status.getLen());
        for (BlockLocation block : blocks) {
            log.debug(pathStr + "集群存储位置 = " + Arrays.toString(block.getHosts()) + "\t" + Arrays.toString(block.getNames()));
        }
    }


    /**
     * 获取校验文件的md5
     */
    public static String chechMD5(String pathStr) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Path dstPath = new Path(pathStr);
        byte[] buffer = new byte[2097152];
        int c;

        try (FSDataInputStream in = dfs.open(dstPath)) {
            while ((c = in.read(buffer)) != -1) {
                md5.update(buffer, 0, c);
            }

            BigInteger bi = new BigInteger(1, md5.digest());
            return bi.toString(16);
        }
    }


}
