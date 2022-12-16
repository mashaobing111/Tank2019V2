package com.msb.tank.strategy;


import com.msb.tank.Bullet;
import com.msb.tank.Player;
import com.msb.tank.ResourceMgr;
import com.msb.tank.TankFrame;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 16:10
 * @description: com.msb.tank.strategy
 * @version: 1.0
 */
public class DefaultFireStrategy implements FireStrategy {

    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(bX,bY,p.getDirection(),p.getGroup()));
    }
}
