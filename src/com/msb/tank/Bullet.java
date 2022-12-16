package com.msb.tank;

import java.awt.*;

/**
 * @author: msb
 * @date: 2022/12/11 - 12 - 11 - 18:13
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Bullet {
    private  int x, y;
    private Dir dir;
    private Group group;
    private boolean living = true;
    public static final int SPEED = 10;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }
    public void paint(Graphics g) {
        /*if (!living) TankFrame.INSTANCE.getBullets().remove(this);*/
        switch (dir) {
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
        }
        move();
    }

    private void move() {
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
        //子弹边界检测
        bulletBoundsCheck();
    }
    private void bulletBoundsCheck() {
        if (x > TankFrame.INSTANCE.GAME_WIDTH || x < 0 || y > TankFrame.INSTANCE.GAME_HEIGHT || y < 0) living = false;//子弹边界检测
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public void collidesWithTank(Tank tank){
        if (!this.isLiving() || !tank.isLive()) return;
        Rectangle rectBullet = new Rectangle(x, y, ResourceMgr.bulletU.getWidth(), ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.goodTankU.getWidth(), ResourceMgr.goodTankU.getHeight());

        if (this.group != tank.getGroup()) {
            if (rectBullet.intersects(rectTank)) {
                this.die();
                tank.die();
            }
        }

    }
    public void die(){
        this.setLiving(false);
    }
}
