import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.spc.common.util.hdfsUtil.HdfsUpDownloadUtil;
import xyz.spc.serve.guest.GuestAPP;

import java.io.IOException;

@SpringBootTest(classes = GuestAPP.class)
public class MyTest {

    @Test
    public void test() throws IOException {
        System.out.println("test: ");
        HdfsUpDownloadUtil.downFile("hdfs://localhost:9000/user/spade/TEST/测试文件.txt", "D:/LIB/测试文件.txt");
    }
}
