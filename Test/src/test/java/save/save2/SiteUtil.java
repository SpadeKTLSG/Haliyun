package save.save2;

import java.io.IOException;
import java.util.Properties;

public class SiteUtil {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(SiteUtil.class.getClassLoader().getResourceAsStream("officeToSwf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readUrl(String key) {
        return (String) properties.get(key);
    }
}
