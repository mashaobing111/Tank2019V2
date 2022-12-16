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
public class Tank {
    private int x , y ;
    private Dir dir = Dir.U;
    private boolean bU, bD, bL, bR;
    private  boolean moving = true;
    private Group group = Group.GOOD;
    public static final int SPEED = 5;
    private int tankWidth, tankHeight;
    private  boolean live  = true;
    private  int oldX, oldY;
    private Random random = new Random();
    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.oldX = x;
        this.oldY = y;
        this.tankWidth = ResourceMgr.goodTankU.getWidth();
        this.tankHeight =  ResourceMgr.goodTankU.getHeight();
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
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        if (moving) return;
        oldX = x;
        oldY = y;
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
        //随机方向
        randomDir();
        //随机开火
        if (random.nextInt(100) >98){
            this.fire();
        }

    }

    private void fire() {//开火
        int bX = x + tankWidth/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = y + tankHeight/2 - ResourceMgr.bulletU.getHeight()/2;
       TankFrame.INSTANCE.add(new Bullet(bX,bY,dir,group));
    }

    private void tankBoundsCheck() {//坦克边缘检查
        if (x < 1 || y < tankHeight -20 || x > TankFrame.INSTANCE.GAME_WIDTH - tankWidth-1 || y > TankFrame.INSTANCE.GAME_HEIGHT - tankHeight-1) {
            this.back();
        }

    }

    private void back() {
        this.x = oldX;
        this.y = oldY;
    }

    public void die() {
        this.setLive(false);
        int eX = x + tankWidth/2 - ResourceMgr.explodes[0].getWidth()/2;
        int eY = y + tankHeight/2 - ResourceMgr.explodes[0].getHeight()/2;
        TankFrame.INSTANCE.add(new Explode(eX, eY));
    }

    //随机方向
    private void randomDir(){
        if (random.nextInt(100) >95)
            this.dir = Dir.randomDir();
    }

}
