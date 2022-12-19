package com.msb.tank;

import com.msb.tank.chainofresponsibility.Collider;
import com.msb.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static final TankFrame INSTANCE = new TankFrame();
    public static final int GAME_WIDTH = 1300, GAME_HEIGHT = 800;

    //解决屏幕闪烁问题  双缓冲
    Image offScreenImage = null;

    private GameModel gm = new GameModel();

    private TankFrame() {
        this.setTitle("Tank War");
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setLocation(300, 100);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //添加键盘监听
        this.addKeyListener(new TankKeyListener());
    }

    @Override
    public void paint(Graphics g) {//画笔让自己去处理，不在TankFrame的paint去画坦克 ，让Tank自己去画
        gm.paint(g);

    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);//内存的画笔画完以后
        g.drawImage(offScreenImage, 0, 0, null);//显存的画笔直接把内存中画好的直接显示出来
    }


    //键盘监听内部类
    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {//按键监听 与画笔一样 也是让他们自己去处理
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_P){
                save();
            }else if (key == KeyEvent.VK_L){
                load();
            }else {
                gm.getMyTank().keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }
    }

    private void load() {
        ObjectInputStream ois = null;
        try {
            File f = new File("c:/test/tank.dat");
            FileInputStream fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            gm = (GameModel)(ois.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null){
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void save() {

        ObjectOutputStream oos = null;
        try {
            File f = new File("c:/test/tank.dat");
            FileOutputStream fos = new FileOutputStream(f);
             oos = new ObjectOutputStream(fos);
            oos.writeObject(gm);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            try {
                if (oos != null){
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public GameModel getGm(){
        return this.gm;
    }
}
