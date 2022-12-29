package com.msb.tank;

import com.msb.tank.net.BulletNewMsg;
import com.msb.tank.net.Client;
import com.msb.tank.net.TankMoveOrDirChangeMsg;
import com.msb.tank.net.TankStopMsg;
import com.msb.tank.strategy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 14:50
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Player extends AbstractGameObject {
    public static final int SPEED = 5;
    private int x , y ;
    private Dir dir;
    private boolean bU, bD, bL, bR;
    private  boolean moving = false;
    private Group group;
    private int tankWidth = ResourceMgr.goodTankU.getWidth(), tankHeight =  ResourceMgr.goodTankU.getHeight();
    private  boolean live  = true;
    private UUID id = UUID.randomUUID();
    private Rectangle rect;
    private FireStrategy fireStrategy;

    public Rectangle getRect() {
        return rect;
    }

    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        //init fire strategy from config file
        this.initFireStrategy();
        rect = new Rectangle(x,y,tankWidth,tankHeight);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
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
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.drawString(id.toString(),x,y - 10);
        g.setColor(c);
        switch (dir) {
            case U:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            case L:
                g.drawImage(this.group.equals(Group.GOOD) ?  ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
        }
        move();
        rect.x = x;
        rect.y = y;
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
        //坦克边界检测
        tankBoundsCheck();

    }

    private void setMainDir() {
        boolean oldMoving = moving;
        Dir oldDir = dir;
        if(!bU && !bD && !bL && !bR ){
            moving = false;
            Client.INSTANCE.send(new TankStopMsg(this.id,this.x,this.y));
        }else{
            moving = true;

        if(bU){
            dir = Dir.U;
        }
        if(bD){
            dir = Dir.D;
        }
        if(bL ){
            dir = Dir.L;
        }
        if(bR ){
            dir = Dir.R;
        }
        //old status is not moving , now my tank will move immediate
        if (!oldMoving) Client.INSTANCE.send(new TankMoveOrDirChangeMsg(this.id,this.x,this.y,this.dir));
        if (!this.dir.equals(oldDir)) Client.INSTANCE.send(new TankMoveOrDirChangeMsg(this.id,this.x,this.y,this.dir));
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
    public void die() {
        this.setLive(false);
        int eX = x + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.explodes[0].getWidth()/2;
        int eY = y + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.explodes[0].getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Explode(eX, eY));
    }
}
