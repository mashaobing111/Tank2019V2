package com.msb.tank.net;

import com.msb.tank.*;

import java.io.*;
import java.util.UUID;

/**
 * @author: msb
 * @date: 2022/12/24 - 12 - 24 - 17:05
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class TankJoinMsg {
    private int x, y;
    private Direction dir;
    private boolean moving;
    private Group group;

    private UUID id; //self's id

    public TankJoinMsg(){

    }

    public TankJoinMsg(Player p){
        this.x = p.getX();
        this.y = p.getY();
        this.dir = p.getDirection();
        this.moving = p.isMoving();
        this.group = p.getGroup();
        this.id = p.getId();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public byte[] toBytes(){

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null){
                baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (dos != null){
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Direction.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(),dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    public void handle() {
        //is this msg's id equals my tank's id  return
        //otherwise add new tank to my collection

        if (this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;
        if (TankFrame.INSTANCE.getGm().findTankByUUID(this.id) != null) return;
        Tank t = new Tank(this);

        TankFrame.INSTANCE.getGm().add(t);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
    }
}
