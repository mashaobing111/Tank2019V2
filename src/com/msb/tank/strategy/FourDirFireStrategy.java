package com.msb.tank.strategy;

import com.msb.tank.*;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 16:34
 * @description: com.msb.tank.strategy
 * @version: 1.0
 */
public class FourDirFireStrategy implements FireStrategy {

    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        Direction[] dirs = Direction.values();
        for (Direction d : dirs){
        TankFrame.INSTANCE.getGm().add(new Bullet(bX,bY,d,p.getGroup()));
        }
    }
}
