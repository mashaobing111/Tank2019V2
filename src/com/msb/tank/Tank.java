package com.msb.tank;

import com.msb.tank.net.Client;
import com.msb.tank.net.TankDieMsg;
import com.msb.tank.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 14:50
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Tank extends AbstractGameObject {
    public static final int SPEED = 5;
    private int x;
    private int y ;
    private Dir dir = Dir.U;
    private boolean bU, bD, bL, bR;
    private  boolean moving = true;
    private Group group = Group.GOOD;
    private  boolean live  = true;
    private  int oldX, oldY;
    private Rectangle rect;
    private int w = ResourceMgr.goodTankU.getWidth();
    private int h = ResourceMgr.goodTankU.getHeight();
    private Random random = new Random();
    private UUID id ;
    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.oldX = x;
        this.oldY = y;
        rect = new Rectangle(x, y, w, h);
    }
    public Tank(TankJoinMsg tankJoinMsg) {
        this.x = tankJoinMsg.getX();
        this.y = tankJoinMsg.getY();
        this.dir = tankJoinMsg.getDir();
        this.moving = tankJoinMsg.isMoving();
        this.group = tankJoinMsg.getGroup();
        this.id = tankJoinMsg.getId();
        this.oldX = x;
        this.oldY = y;
        this.rect = new Rectangle(x, y, w, h);
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.drawString(id.toString(),x,y - 10);
        g.setColor(c);
        switch (dir) {
            case U:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            case L:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
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
        //??????????????????
        tankBoundsCheck();
        /*//????????????
        randomDir();
        //????????????
        if (random.nextInt(100) >98){
            this.fire();
        }*/

    }

    private void fire() {//??????
        int bX = x + this.w/2 - Bullet.W/2;
        int bY = y + this.h/2 - Bullet.H/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(this.id, bX,bY, dir,group));
    }

    private void tankBoundsCheck() {//??????????????????
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

    //????????????
    private void randomDir(){
        if (random.nextInt(100) >95)
            this.dir = Dir.randomDir();
    }
    public Rectangle getRect(){
        return rect;
    }
}
