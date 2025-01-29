import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSTest {


    //Hadoop select
    @Test
    public void testHDFS() throws URISyntaxException, IOException {
        //读取配置文件
        Configuration conf = new Configuration();
        // 文件系统
        FileSystem fs = null;
        String hdfsUri = "hdfs://localhost:9000";


        // 返回指定的文件系统,如果在本地测试，需要使用此种方法获取文件系统

        URI uri = new URI(hdfsUri.trim());
        fs = FileSystem.get(uri, conf);

    }

}
