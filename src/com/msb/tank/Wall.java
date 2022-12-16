package com.msb.tank;

import com.msb.tank.AbstractGameObject;

import java.awt.*;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 18:10
 * @description: com.msb.tank.strategy
 * @version: 1.0
 */
public class Wall extends AbstractGameObject {
    private int x, y, w, h;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }
}
