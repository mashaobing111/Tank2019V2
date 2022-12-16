package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 14:50
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Player {
    private int x , y ;
    private Dir dir;
    private boolean bU, bD, bL, bR;
    private  boolean moving = false;
    private Group group;
    public static final int SPEED = 5;
    private int tankWidth = ResourceMgr.goodTankU.getWidth(), tankHeight =  ResourceMgr.goodTankU.getHeight();
    private  boolean live  = true;
    private Random random = new Random();


    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Group getGroup() {
        return group;
    }
    public void paint(Graphics g) {
        if (!this.isLive()) return;
        switch (dir) {
            case U:
                g.drawImage(ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.goodTankD, x, y, null);
                break;
            case L:
                g.drawImage(ResourceMgr.goodTankL, x, y, null);
                break;
        }
        move();
    }


    public void keyPressed(KeyEvent e) {
        //获取按下的键值  根据按键给出坦克方向
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP://↑当前键值
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
        }
        setMainDir();
    }

    public void keyReleased(KeyEvent e) {
        //获取抬起的键值
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP://↑当前键值
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_SPACE:
                fire();
        }
        setMainDir();
    }

    private void move() {
        if (!moving) return;

        switch (dir){
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;

        }
        //坦克边界检测
        tankBoundsCheck();

    }

    private void setMainDir() {
        if(!bU && !bD && !bL && !bR ){
            moving = false;

        }else{
            moving = true;
        }
        if(bU){
            dir = Dir.U;
        }
        if(bD){
            dir = Dir.D;
        }
        if(bL ){
            dir = Dir.L;
        }
        if(bR ){
            dir = Dir.R;
        }

    }

    private void fire() {//开火
        int bX = x + tankWidth/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = y + tankHeight/2 - ResourceMgr.bulletU.getHeight()/2;
       TankFrame.INSTANCE.add(new Bullet(bX,bY,dir,group));
    }

    private void tankBoundsCheck() {//坦克边缘检查
        if (x > TankFrame.INSTANCE.GAME_WIDTH - tankWidth-1) x = TankFrame.INSTANCE.GAME_WIDTH - tankWidth-1;
        if (x < 1) x = 1;
        if (y > TankFrame.INSTANCE.GAME_HEIGHT - tankHeight-1) y = TankFrame.INSTANCE.GAME_HEIGHT - tankHeight-1;
        if (y < tankHeight -20) y = tankHeight-20;
    }
}
