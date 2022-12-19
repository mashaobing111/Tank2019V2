import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author: msb
 * @date: 2022/12/19 - 12 - 19 - 14:58
 * @description: PACKAGE_NAME
 * @version: 1.0
 */
public class SerializeTest {
    @Test
    public void testSave(){

        try {
            T t = new T();
            File f = new File("c:/test/s.dat");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoad(){

        try {

            File f = new File("c:/test/s.dat");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            T t = (T)(ois.readObject());
            System.out.println(t.a + " " + t.b);
            ois.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class T implements Serializable {
    int a = 8;
    int b = 9;
}