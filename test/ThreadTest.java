import org.junit.jupiter.api.Test;

/**
 * @author: msb
 * @date: 2022/12/19 - 12 - 19 - 16:58
 * @description: PACKAGE_NAME
 * @version: 1.0
 */
public class ThreadTest {
    @Test
    public void testThread(){
        new TT().start();
        new Thread(new C()).start();
        //Runnable不需要名称直接在类中新建对象就是匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("anonymous");
            }
        }).start();
        new Thread(()->{
            System.out.println("hello");
        }).start();//java8 lambda expression
    }
}
class TT extends Thread{
    @Override
    public void run(){
        System.out.println("TT");
    }
}

class C implements Runnable{

    @Override
    public void run() {
        System.out.println("C");
    }
}
