package com.msb.tank.net;

import com.msb.tank.Dir;
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
class TankMoveOrDirChangeMsgTest {
    @Test
    void encode(){
        //测试使用 只能在本地输出
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());

        TankMoveOrDirChangeMsg msg = new TankMoveOrDirChangeMsg(UUID.randomUUID(),50,40,Dir.D);
        //将数据输出
        ch.writeOutbound(msg);

        //获取数据
        ByteBuf buf = ch.readOutbound();

        //按照写入顺序；
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        //枚举通过获取下标值获取
        Dir dir = Dir.values()[buf.readInt()];

        //断言读出数据是否正确
        assertEquals(MsgType.TankMoveOrDirChange,msgType);
        assertEquals(28,length);
        assertEquals(msg.getId(),uuid);
        assertEquals(50,x);
        assertEquals(40,y);
        assertEquals(Dir.D,dir);

    }

    @Test
    void decoder(){
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankMoveOrDirChange.ordinal());
        buf.writeInt(28);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(50);
        buf.writeInt(40);
        buf.writeInt(Dir.D.ordinal());

        ch.writeInbound(buf);

        TankMoveOrDirChangeMsg msg = ch.readInbound();

        assertEquals(id,msg.getId());
        assertEquals(50,msg.getX());
        assertEquals(40,msg.getY());
        assertEquals(Dir.D,msg.getDir());

    }
}