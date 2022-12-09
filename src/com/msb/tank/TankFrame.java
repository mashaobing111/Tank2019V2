package com.msb.tank;

import java.awt.*;
import java.awt.event.*;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 10:14
 * @description: com.msb.tank
 * @version: 1.0
 */
/*
继承下来可以重写一些方法

* */
public class TankFrame extends Frame {
    //我方坦克1
    private Tank myTank;
    //敌方坦克1
    private Tank enemy;
    public TankFrame(){
        this.setTitle("Tank War");
        this.setSize(1300,800);
        this.setLocation(300,100);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new TankKeyListener());
        myTank = new Tank(300,730, Dir.U);
        enemy = new Tank(300,50,Dir.D);
    }

    @Override
    public void paint(Graphics g) {//画笔让自己去处理，不在TankFrame的paint去画坦克 ，让Tank自己去画
        myTank.paint(g);
        enemy.paint(g);
    }


    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {//按键监听 与画笔一样 也是让他们自己去处理
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
