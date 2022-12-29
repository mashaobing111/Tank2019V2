package com.msb.tank;

import com.msb.tank.net.BulletNewMsg;
import com.msb.tank.net.Client;

import java.awt.*;
import java.util.UUID;

/**
 * @author: msb
 * @date: 2022/12/11 - 12 - 11 - 18:13
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Bullet extends AbstractGameObject {
    public static final int SPEED = 10;
    public static final int W = ResourceMgr.bulletU.getWidth();
    public static final int H = ResourceMgr.bulletU.getHeight();
    private  int x, y;
    private Dir dir;
    private Group group;
    private boolean live = true;
    private Rectangle rect;
    private UUID id = UUID.randomUUID();
    private UUID playId;
    public Bullet(UUID playId, int x, int y, Dir dir, Group group) {
        this.playId = playId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        rect = new Rectangle(x, y, W, H);

    }

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlayerId(){
        return this.playId;
    }
    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
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
                ", direction=" + dir +
                ", group=" + group +
                ", living=" + live +
                ", rect=" + rect +
                ", w=" + W +
                ", h=" + H +
                '}';
    }
}
