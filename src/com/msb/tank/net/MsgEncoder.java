package com.msb.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: msb
 * @date: 2022/12/26 - 12 - 26 - 15:40
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        //首先获取消息类型
        byteBuf.writeInt(msg.getMsgType().ordinal());
        //把TankJoinMsg的数据传到字节数组中；
        byte[] bytes = msg.toBytes();
        //要知道字节数组的长度  然后把长度写过去
        byteBuf.writeInt(bytes.length);
        //在写数据
        byteBuf.writeBytes(bytes);
    }
}
