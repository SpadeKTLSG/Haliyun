import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.infra.mapper.Guest.users.UserMapper;
import xyz.spc.serve.guest.GuestAPP;

@SpringBootTest(classes = GuestAPP.class)
public class MyTest {

    @MockBean
    private UserMapper userMapper;


    @Test
    public void test() throws Exception {
        System.out.println("test: ");
    }

    @Test
    public void funcTest() {
        userMapper.delete(Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getAccount, "logic_test_5"));
    }

}
