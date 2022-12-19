package com.msb.tank.chainofresponsibility;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.ResourceMgr;
import com.msb.tank.Tank;

import java.awt.*;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 20:09
 * @description: com.msb.tank.chainofresponsibility
 * @version: 1.0
 */
public class  BulletTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject ago1, AbstractGameObject ago2) {
        if (ago1 instanceof Bullet && ago2 instanceof Tank){
            Bullet b = (Bullet) ago1;
            Tank t = (Tank) ago2;

            if (!b.isLive() || !t.isLive()) return false;
            if (b.getGroup() == t.getGroup()) return true;

            if (b.getRect().intersects(t.getRect())) {
                b.die();
                t.die();
                return false;
            }
        }else if (ago1 instanceof Tank && ago2 instanceof Bullet){
            return collide(ago2,ago1);
        }
        return true;
    }
}
