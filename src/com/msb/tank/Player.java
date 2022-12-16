package com.msb.tank;

import com.msb.tank.strategy.DefaultFireStrategy;
import com.msb.tank.strategy.FireStrategy;
import com.msb.tank.strategy.FourDirFireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 14:50
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Player extends AbstractGameObject{
    public static final int SPEED = 5;
    private int x , y ;
    private Direction direction;
    private boolean bU, bD, bL, bR;
    private  boolean moving = false;
    private Group group;
    private int tankWidth = ResourceMgr.goodTankU.getWidth(), tankHeight =  ResourceMgr.goodTankU.getHeight();
    private  boolean live  = true;
    private Random random = new Random();
    private FireStrategy fireStrategy;

    public Player(int x, int y, Direction direction, Group group) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        //init fire strategy from config file
        this.initFireStrategy();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Group getGroup() {
        return group;
    }

    public void paint(Graphics g) {
        if (!this.isLive()) return;
        switch (direction) {
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

    private void move() {
        if (!moving) return;

        switch (direction){
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
        //坦克边界检测
        tankBoundsCheck();

    }

    private void setMainDir() {
        if(!bU && !bD && !bL && !bR ){
            moving = false;

        }else{
            moving = true;
        }
        if(bU){
            direction = Direction.U;
        }
        if(bD){
            direction = Direction.D;
        }
        if(bL ){
            direction = Direction.L;
        }
        if(bR ){
            direction = Direction.R;
        }

    }

    private void initFireStrategy(){
        ClassLoader loader = Player.class.getClassLoader();

        String className = PropertyMgr.get("tankFireStrategy");
        try {
            Class clazz = loader.loadClass("com.msb.tank.strategy." + className );
            fireStrategy = (FireStrategy) (clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fire() {//开火
        //read config
        //if default four dir
        //多态 父类引用指向子类对象

        fireStrategy.fire(this);
    }

    private void tankBoundsCheck() {//坦克边缘检查
        if (x > TankFrame.INSTANCE.GAME_WIDTH - tankWidth-1) x = TankFrame.INSTANCE.GAME_WIDTH - tankWidth-1;
        if (x < 1) x = 1;
        if (y > TankFrame.INSTANCE.GAME_HEIGHT - tankHeight-1) y = TankFrame.INSTANCE.GAME_HEIGHT - tankHeight-1;
        if (y < tankHeight -20) y = tankHeight-20;
    }
}
