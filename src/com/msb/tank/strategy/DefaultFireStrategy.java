package com.msb.tank.strategy;


import com.msb.tank.*;
import com.msb.tank.net.BulletNewMsg;
import com.msb.tank.net.Client;

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
        Bullet b = new Bullet(p.getId(), bX,bY,p.getDir(),p.getGroup());
        TankFrame.INSTANCE.getGm().add(b);
        //send a bullet new msg to server when a bullet is born;
        Client.INSTANCE.send(new BulletNewMsg(b));
    }
}
