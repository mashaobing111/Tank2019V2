package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 14:50
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Tank {
    private int x , y ;
    private Dir dir = Dir.U;
    private boolean bU, bD, bL, bR;
    private  boolean moving = false;
    private Group group = Group.GOOD;
    public static final int SPEED = 5;
    //持有引用
    TankFrame tf;

    public Tank(int x, int y, Dir dir, Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (this.group == Group.GOOD){
            switch (dir) {
                case U:
                    g.drawImage(ResourceMgr.goodTankU, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.goodTankR, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.goodTankD, x, y, null);
                    break;
                case L:
                    g.drawImage(ResourceMgr.goodTankL, x, y, null);
                    break;
            }
        }
        if (this.group == Group.BAD){
            switch (dir) {
                case U:
                    g.drawImage(ResourceMgr.badTankU, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.badTankR, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.badTankD, x, y, null);
                    break;
                case L:
                    g.drawImage(ResourceMgr.badTankL, x, y, null);
                    break;
            }
        }
        move();
    }


    public void keyPressed(KeyEvent e) {
        //获取按下的键值  根据按键给出坦克方向
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP://↑当前键值
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
        }
        setMainDir();
    }

    public void keyReleased(KeyEvent e) {
        //获取抬起的键值
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP://↑当前键值
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_SPACE:
                fire();
        }
        setMainDir();
    }


    private void setMainDir() {
        if(!bU && !bD && !bL && !bR ){
            moving = false;
        }else{
            moving = true;
        }
        if(bU && !bD && !bL && !bR ){
            dir = Dir.U;
        }
        if(!bU && bD && !bL && !bR ){
            dir = Dir.D;
        }
        if(!bU && !bD && bL && !bR ){
            dir = Dir.L;
        }
        if(!bU && !bD && !bL && bR ){
            dir = Dir.R;
        }

    }

    private void move() {
        if (!moving) return;
        switch (dir){
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;

        }
    }
    private void fire() {
       tf.add(new Bullet(x,y,dir,group));
    }

}
