import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.spc.common.util.hdfsUtil.HdfsFuncUtil;
import xyz.spc.serve.guest.GuestAPP;

import java.io.IOException;

@SpringBootTest(classes = GuestAPP.class)
public class MyTest {

    @Test
    public void test() throws IOException {
        System.out.println("test: ");
        HdfsFuncUtil.searchDir("TEST");
    }
}
