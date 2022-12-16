package com.msb.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 10:14
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) {
        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true);
        while (true){
            try {
                TimeUnit.MILLISECONDS.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tf.repaint();  //repaint方法 调用update方法 调用paint方法
        }
    }
}
