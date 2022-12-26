package com.msb.nettycodec1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: msb
 * @date: 2022/12/24 - 12 - 24 - 14:50
 * @description: com.msb.nettycodec1
 * @version: 1.0
 */
public class TankMsgEncoder extends MessageToByteEncoder<TankMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankMsg tankMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(tankMsg.x);
        byteBuf.writeInt(tankMsg.y);
    }
}
