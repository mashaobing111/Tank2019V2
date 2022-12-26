package com.msb.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.BufferedReader;
import java.util.List;

/**
 * @author: msb
 * @date: 2022/12/23 - 12 - 23 - 15:42
 * @description: com.msb.nettycodec
 * @version: 1.0
 */
public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (byteBuf.readableBytes() < 8) return;

        int x = byteBuf.readInt();
        int y = byteBuf.readInt();

        out.add(new TankMsg(x,y));
    }
}
