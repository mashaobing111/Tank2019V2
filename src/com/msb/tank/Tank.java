package com.msb.tank;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 14:50
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Tank extends AbstractGameObject {
    private int x , y ;
    private Direction direction = Direction.U;
    private boolean bU, bD, bL, bR;
    private  boolean moving = true;
    private Group group = Group.GOOD;
    public static final int SPEED = 5;
    private  boolean live  = true;
    private  int oldX, oldY;
    private Rectangle rect;
    private int w = ResourceMgr.goodTankU.getWidth();
    private int h = ResourceMgr.goodTankU.getHeight();
    private Random random = new Random();

    public Tank(int x, int y, Direction direction, Group group) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.oldX = x;
        this.oldY = y;
        rect = new Rectangle(x, y, w, h);
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
        switch (direction) {
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

        /*Color old = g.getColor();
        g.setColor(Color.yellow);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(old);*/

        move();

        rect.x = x;
        rect.y = y;
    }

    private void move() {
        if (!moving) return;
        oldX = x;
        oldY = y;
        switch (direction){
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
        int bX = x + this.w/2 - Bullet.W/2;
        int bY = y + this.h/2 - Bullet.H/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bX,bY, direction,group));
    }

    private void tankBoundsCheck() {//坦克边缘检查
        if (x < 1 || y < this.h -20 || x > TankFrame.INSTANCE.GAME_WIDTH - this.w - 1 || y > TankFrame.INSTANCE.GAME_HEIGHT - this.h-1) {
            this.back();
        }

    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    public void die() {
        this.setLive(false);
        int eX = x + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.explodes[0].getWidth()/2;
        int eY = y + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.explodes[0].getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Explode(eX, eY));
    }

    //随机方向
    private void randomDir(){
        if (random.nextInt(100) >95)
            this.direction = Direction.randomDir();
    }
    public Rectangle getRect(){
        return rect;
    }
}
