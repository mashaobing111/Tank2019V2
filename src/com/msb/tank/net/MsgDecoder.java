package com.msb.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: msb
 * @date: 2022/12/26 - 12 - 26 - 16:46
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 37) return;

        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        TankJoinMsg tjm = new TankJoinMsg();
        tjm.parse(bytes);

        list.add(tjm);
    }
}
