package com.msb.tank.net;

import com.msb.tank.*;

import java.io.*;
import java.util.UUID;

/**
 * @author: msb
 * @date: 2022/12/28 - 12 - 28 - 14:15
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class BulletNewMsg extends Msg{

    private UUID playerId;
    private UUID id; //self's id
    private int x, y;
    private Dir dir;
    private Group group;


    public BulletNewMsg() {
    }

    public BulletNewMsg(Bullet bullet) {
        this.playerId = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();

    }

    public byte[] toBytes(){

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(playerId.getMostSignificantBits());
            dos.writeLong(playerId.getLeastSignificantBits());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());

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
        DataInputStream dis = null;

        try {
            dis = new DataInputStream(new ByteArrayInputStream(bytes));
            this.playerId = new UUID(dis.readLong(),dis.readLong());
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "playerId=" + playerId +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }

    public void handle() {
        if (this.playerId.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;
        Bullet bullet = new Bullet(this.playerId,x,y,dir,group);
        bullet.setId(this.id);
        TankFrame.INSTANCE.getGm().add(bullet);

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }
}
