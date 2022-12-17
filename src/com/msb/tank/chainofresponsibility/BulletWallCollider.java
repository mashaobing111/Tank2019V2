package com.msb.tank.chainofresponsibility;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.Wall;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 20:31
 * @description: com.msb.tank.chainofresponsibility
 * @version: 1.0
 */
public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject ago1, AbstractGameObject ago2) {
        if (ago1 instanceof Bullet && ago2 instanceof Wall){
//            System.out.println(ago1);
//            System.out.println(ago2);
            Bullet b = (Bullet) ago1;
            Wall w = (Wall) ago2;
            if (b.isLive()){
                if (b.getRect().intersects(w.getRect())){
                    b.die();
                    return false;
                }
            }
        }else if (ago1 instanceof Wall && ago2 instanceof Bullet){
            return collide(ago2,ago1);
        }
        return true;
    }
}
