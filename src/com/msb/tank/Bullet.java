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
    public static final int SPEED = 3;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
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
    }
}
