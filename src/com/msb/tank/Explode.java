package com.msb.tank;

import java.awt.*;
import java.io.Serializable;

/**
 * @author: msb
 * @date: 2022/12/11 - 12 - 11 - 18:13
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Explode extends AbstractGameObject {
    private int x, y;
    private int width, height;
    private int step = 0;
    private boolean live = true;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
        new Thread(()->new Audio("audio/tank_explode.wav").run()).start();
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
        if (!live) return;
        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;
        if (step >= ResourceMgr.explodes.length) {
            this.die();
        }
    }

    private void die() {
        this.live = false;
    }
}
