import java.io.IOException;
import java.util.Properties;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 14:15
 * @description: PACKAGE_NAME
 * @version: 1.0
 */
public class TestConfig {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            //通过load方法把config所有的键值对装到内存里
            props.load(TestConfig.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = (String)props.get("initTankCount");
        System.out.println(str);
    }
}
