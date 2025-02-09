package xyz.spc.common.util.hdfsUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

/**
 * HDFS上下文, 存储HDFS配置 (提升至Common模块以便于调用)
 */
@Slf4j
public final class HdfsContext {
    /**
     * 文件系统
     */
    private FileSystem fileSystem = null;
    /**
     * 配置
     */
    private Configuration configuration = null;

    private HdfsContext() {
        try {
            configuration = new Configuration();
            configuration.set("fs.defaultFS", "hdfs://localhost:9000/");
            fileSystem = FileSystem.get(configuration);
            log.debug("HDFS初始化成功!");
        } catch (IOException e) {
            log.warn("HDFS初始化失败! :", e);
        }
    }

    public static FileSystem getFileSystem() {
        return SingletonHolder.INSTANCE.fileSystem;
    }

    public static Configuration getConfiguration() {
        return SingletonHolder.INSTANCE.configuration;
    }


    private static class SingletonHolder {
        private static final HdfsContext INSTANCE = new HdfsContext();
    }
}
