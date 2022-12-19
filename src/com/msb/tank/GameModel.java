package com.msb.tank;

import com.msb.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: msb
 * @date: 2022/12/19 - 12 - 19 - 14:17
 * @description: com.msb.tank
 * @version: 1.0
 */
public class GameModel {
    //添加游戏物体容器
    List<AbstractGameObject> gameObjects;
    //我方坦克1
    private Player myTank;
    //碰撞责任链
    ColliderChain cc = new ColliderChain();

    public GameModel(){
        initGameObject();
    }

    private void initGameObject() {
        myTank = new Player(300, 730, Direction.U, Group.GOOD);
        gameObjects = new ArrayList<>();
        /*enemyTanks = new ArrayList<>();
        bullets = new ArrayList<>();
        explodes = new ArrayList<>();*/

        int enemyTanksCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i = 0; i < enemyTanksCount; i++) {
            this.add(new Tank(100 + i * 100, 50, Direction.D, Group.BAD));
        }
        this.add(new Wall(300,200,400,50));
    }

    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("gameObjects：" + gameObjects.size(), 10, 50);
//        g.drawString("坦克的数量：" + enemyTanks.size(), 10, 70);
        g.setColor(c);

        for (int i = 0; i <gameObjects.size() ; i++) {
            if (!gameObjects.get(i).isLive()) {
                gameObjects.remove(i);
                break;
            }
            AbstractGameObject go1 = gameObjects.get(i);
            for (int j = i+1; j <gameObjects.size() ; j++) {
                AbstractGameObject go2 = gameObjects.get(j);
                cc.collide(go1, go2);
            }
            if (gameObjects.get(i).isLive()) {
                gameObjects.get(i).paint(g);
            }
        }
        myTank.paint(g);
    }

    //添加游戏物体
    public void add(AbstractGameObject go){
        gameObjects.add(go);
    }

    public Player getMyTank(){
        return myTank;
    }
}
