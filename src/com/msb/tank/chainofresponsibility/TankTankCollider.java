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
public class TankTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject ago1, AbstractGameObject ago2) {
        if (ago1 != ago2 && ago1 instanceof Tank && ago2 instanceof Tank) {
            Tank t1 = (Tank) ago1;
            Tank t2 = (Tank) ago2;
            if (!t1.isLive() || !t2.isLive()) return false;
            if (t1.getRect().intersects(t2.getRect())) {
                    t1.back();
                    t2.back();
            }
        }
        return true;
    }
}