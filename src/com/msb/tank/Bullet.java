package com.msb.tank;

import java.awt.*;

/**
 * @author: msb
 * @date: 2022/12/11 - 12 - 11 - 18:13
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Bullet extends AbstractGameObject{
    public static final int SPEED = 10;
    private  int x, y;
    private Direction direction;
    private Group group;
    private boolean live = true;
    private Rectangle rect;
    private int w = ResourceMgr.bulletU.getWidth();
    private int h = ResourceMgr.bulletU.getHeight();

    public Bullet(int x, int y, Direction direction, Group group) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        rect = new Rectangle(x, y, w, h);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {

        switch (direction) {
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
        //子弹边框
        /*Color old = g.getColor();
        g.setColor(Color.yellow);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(old);*/
        move();

        //update the rect
        rect.x = x;
        rect.y = y;


    }

    private void move() {
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
        //子弹边界检测
        bulletBoundsCheck();
    }
    private void bulletBoundsCheck() {
        if (x > TankFrame.INSTANCE.GAME_WIDTH || x < 0 || y > TankFrame.INSTANCE.GAME_HEIGHT || y < 0) live = false;//子弹边界检测
    }
    
    public Rectangle getRect(){
        return rect;
    }

    public void die(){
        this.setLive(false);
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", group=" + group +
                ", living=" + live +
                ", rect=" + rect +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
