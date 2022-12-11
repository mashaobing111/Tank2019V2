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
    private  Bullet bullet;
    public static final int GAME_WIDTH = 1300, GAME_HEIGHT = 800;

    public TankFrame(){
        this.setTitle("Tank War");
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setLocation(300,100);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //添加键盘监听
        this.addKeyListener(new TankKeyListener());
        myTank = new Tank(300,730, Dir.U, Group.GOOD,this);
        enemy = new Tank(300,50,Dir.D, Group.BAD,this);
        bullet = new Bullet(300,50,Dir.D, Group.BAD);
    }

    @Override
    public void paint(Graphics g) {//画笔让自己去处理，不在TankFrame的paint去画坦克 ，让Tank自己去画
        myTank.paint(g);
        enemy.paint(g);
        bullet.paint(g);
    }
    //解决屏幕闪烁问题  双缓冲
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);//内存的画笔画完以后
        g.drawImage(offScreenImage, 0, 0, null);//显存的画笔直接把内存中画好的直接显示出来
    }

    //键盘监听内部类
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

    public void add(Bullet bullet){
        this.bullet = bullet;
    }
}
