package com.msb.nettycodec1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: msb
 * @date: 2022/12/24 - 12 - 24 - 14:54
 * @description: com.msb.nettycodec1
 * @version: 1.0
 */
public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) return;

        int x = byteBuf.readInt();
        int y = byteBuf.readInt();

        list.add(new TankMsg(x,y));
    }
}
