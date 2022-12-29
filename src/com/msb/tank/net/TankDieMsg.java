package com.msb.tank.net;

import com.msb.tank.Bullet;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @author: msb
 * @date: 2022/12/27 - 12 - 27 - 14:19
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class TankDieMsg extends Msg{
    private UUID id;
    private UUID bulletId;


    public TankDieMsg() {
    }

    public TankDieMsg(UUID id, UUID bulletId) {
        this.id = id;
        this.bulletId = bulletId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBulletId() {
        return bulletId;
    }

    public void setBulletId(UUID bulletId) {
        this.bulletId = bulletId;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (baos != null) baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = null;

        try {
            dis =  new DataInputStream(new ByteArrayInputStream(bytes));
            this.bulletId = new UUID(dis.readLong(),dis.readLong());
            this.id = new UUID(dis.readLong(),dis.readLong());
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
    public void handle() {
        Bullet b = TankFrame.INSTANCE.getGm().findBulletByUUID(this.bulletId);
        if (b != null){
            b.die();
        }

        Tank t = TankFrame.INSTANCE.getGm().findTankByUUID(this.id);
        if (this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())){
            TankFrame.INSTANCE.getGm().getMyTank().die();
        }else {
            if (t != null){
                t.die();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "id=" + id +
                ", bulletId=" + bulletId +
                '}';
    }
}

