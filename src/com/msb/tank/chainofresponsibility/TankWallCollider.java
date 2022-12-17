package com.msb.tank.chainofresponsibility;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.Tank;
import com.msb.tank.Wall;

/**
 * @author: msb
 * @date: 2022/12/17 - 12 - 17 - 15:22
 * @description: com.msb.tank.chainofresponsibility
 * @version: 1.0
 */
public class TankWallCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject ago1, AbstractGameObject ago2) {
        if (ago1 instanceof Tank && ago2 instanceof Wall) {
            Tank t = (Tank) ago1;
            Wall w = (Wall) ago2;
            if (t.isLive()) {
                if (t.getRect().intersects(w.getRect())) {
                    t.back();
                }
            }
        }else if (ago1 instanceof Wall &&ago2 instanceof Tank){
            collide(ago2,ago1);
        }
        return true;
    }
}