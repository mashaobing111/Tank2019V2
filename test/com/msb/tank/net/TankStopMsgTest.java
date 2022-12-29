package com.msb.tank.net;

import com.msb.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author: msb
 * @date: 2022/12/26 - 12 - 26 - 15:50
 * @description: com.msb.tank.net
 * @version: 1.0
 */
class TankStopMsgTest {
    @Test
    void encode(){
        //测试使用 只能在本地输出
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());

        TankStopMsg msg = new TankStopMsg(UUID.randomUUID(),50,40);
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


        //断言读出数据是否正确
        assertEquals(MsgType.TankStop,msgType);
        assertEquals(24,length);
        assertEquals(msg.getId(),uuid);
        assertEquals(50,x);
        assertEquals(40,y);


    }

    @Test
    void decoder(){
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        buf.writeInt(24);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(50);
        buf.writeInt(40);


        ch.writeInbound(buf);

        TankStopMsg msg = ch.readInbound();

        assertEquals(id,msg.getId());
        assertEquals(50,msg.getX());
        assertEquals(40,msg.getY());


    }
}