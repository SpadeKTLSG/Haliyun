import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.spc.serve.guest.GuestAPP;

@SpringBootTest(classes = GuestAPP.class)
public class MyTest {

    @Test
    public void test() throws Exception {
        System.out.println("test: ");
//
//        HdfsUpDownloadUtil.downFile("TEST/测试文件.txt", HdfsContext.PC_WORK_DIR + "TEST/测试文件.txt");
//        HdfsFuncUtil.deleteF("TEST/测试文件2.txt");
//        System.out.println(HdfsQueryUtil.chechMD5("TEST/测试文件.txt"));

    }
}
