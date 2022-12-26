package com.msb.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: msb
 * @date: 2022/12/23 - 12 - 23 - 15:34
 * @description: com.msb.nettycodec
 * @version: 1.0
 */
public class TankMsgEncoder extends MessageToByteEncoder<TankMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TankMsg tankMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(tankMsg.x);
        byteBuf.writeInt(tankMsg.y);
    }
}
