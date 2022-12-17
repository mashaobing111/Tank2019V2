package com.msb.tank;

import com.msb.tank.chainofresponsibility.Collider;
import com.msb.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.*;
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
    /*//敌方坦克1
    private List<Tank> enemyTanks;
    private List<Bullet> bullets;
    private List<Explode> explodes;*/
    List<AbstractGameObject> gameObjects;
    //解决屏幕闪烁问题  双缓冲
    Image offScreenImage = null;
    //我方坦克1
    private Player myTank;
    ColliderChain cc = new ColliderChain();


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
        initGameObject();

    }

    private void initGameObject() {
        myTank = new Player(300, 730, Direction.U, Group.GOOD);
        gameObjects = new ArrayList<>();
        /*enemyTanks = new ArrayList<>();
        bullets = new ArrayList<>();
        explodes = new ArrayList<>();*/

        int enemyTanksCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i = 0; i < enemyTanksCount; i++) {
            this.add(new Tank(100 + i * 100, 50, Direction.D, Group.BAD));
        }
        this.add(new Wall(300,200,400,50));
    }

    @Override
    public void paint(Graphics g) {//画笔让自己去处理，不在TankFrame的paint去画坦克 ，让Tank自己去画
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("gameObjects：" + gameObjects.size(), 10, 50);
//        g.drawString("坦克的数量：" + enemyTanks.size(), 10, 70);
        g.setColor(c);

        for (int i = 0; i <gameObjects.size() ; i++) {
            if (!gameObjects.get(i).isLive()) {
                gameObjects.remove(i);
                break;
            }
            AbstractGameObject go1 = gameObjects.get(i);
            for (int j = i+1; j <gameObjects.size() ; j++) {
                AbstractGameObject go2 = gameObjects.get(j);
                cc.collide(go1, go2);
            }
            if (gameObjects.get(i).isLive()) {
                gameObjects.get(i).paint(g);
            }
        }
        myTank.paint(g);
        /*for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemyTanks.size(); j++) {
                bullets.get(i).collidesWithTank(enemyTanks.get(j));
            }
            if (!bullets.get(i).isLiving()) {
                bullets.remove(i);
            } else {
                bullets.get(i).paint(g);
            }
        }


        for (int i = 0; i < enemyTanks.size(); i++) {
            if (!enemyTanks.get(i).isLive()) {
                enemyTanks.remove(i);
            } else {
                enemyTanks.get(i).paint(g);
            }
        }
        for (int i = 0; i < explodes.size(); i++) {
            if (!explodes.get(i).isLive()) {
                explodes.remove(i);
            } else {
                explodes.get(i).paint(g);
            }
        }*/
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
//添加游戏物体
    public void add(AbstractGameObject go){
        gameObjects.add(go);
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
}
