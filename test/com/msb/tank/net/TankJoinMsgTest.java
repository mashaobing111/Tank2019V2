package com.msb.tank.net;

import com.msb.nettycodec1.TankMsg;
import com.msb.nettycodec1.TankMsgEncoder;
import com.msb.tank.Direction;
import com.msb.tank.Group;
import com.msb.tank.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: msb
 * @date: 2022/12/26 - 12 - 26 - 15:50
 * @description: com.msb.tank.net
 * @version: 1.0
 */
class TankJoinMsgTest {
    @Test
    void encode(){
        //测试使用 只能在本地输出
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());

        Player p = new Player(50,40, Direction.R, Group.BAD);
        TankJoinMsg tjm = new TankJoinMsg(p);

        //将数据输出
        ch.writeOutbound(tjm);
        //获取数据
        ByteBuf buf = ch.readOutbound();
        //按照写入顺序；
        int length = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        //枚举通过获取下标值获取
        Direction dir = Direction.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        //断言读出数据是否正确
        assertEquals(33,length);
        assertEquals(50,x);
        assertEquals(40,y);
        assertEquals(Direction.R,dir);
        assertFalse(moving);
        assertEquals(Group.BAD,group);
        assertEquals(p.getId(),uuid);
    }

    @Test
    void decoder(){
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(33);
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Direction.D.ordinal());
        buf.writeBoolean(true);
        buf.writeInt(Group.GOOD.ordinal());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());

        ch.writeInbound(buf);

        TankJoinMsg tm = ch.readInbound();

        assertEquals(5,tm.getX());
        assertEquals(8,tm.getY());
        assertEquals(Direction.D,tm.getDir());
        assertTrue(tm.isMoving());
        assertEquals(Group.GOOD,tm.getGroup());
        assertEquals(id,tm.getId());
    }
}