import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.spc.test.TestApp;

@SpringBootTest(classes = TestApp.class)
public class BasicFuncTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("ntxz", "568");
        System.out.println(redisTemplate.opsForValue().get("ntxz"));
    }
}
