package com.msb.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: msb
 * @date: 2022/12/23 - 12 - 23 - 16:25
 * @description: com.msb.nettycodec
 * @version: 1.0
 */
/*
* 单元测试
* */
class TankMsgTest {

    @Test
    void decode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new TankMsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeInt(8);

        ch.writeInbound(buf);

        TankMsg tm = ch.readInbound();
        assertEquals(5,tm.x);
        assertEquals(8,tm.y);
    }
    @Test
    void encode(){
        //测试使用 只能在本地输出
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new TankMsgEncoder());

        TankMsg tm = new TankMsg(5,8);

        ch.writeOutbound(tm);

        ByteBuf buf = ch.readOutbound();

        int x = buf.readInt();
        int y = buf.readInt();

        assertEquals(5,x);
        assertEquals(8,y);
    }
}