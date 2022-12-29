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
        //先确定消息头是否读全
        if (byteBuf.readableBytes() < 8) return;

        byteBuf.markReaderIndex();//标记读指针

        MsgType msgType = MsgType.values()[byteBuf.readInt()];//消息类型
        int length = byteBuf.readInt();//消息长度

        if (byteBuf.readableBytes() < length){ //数据是否读全
            byteBuf.resetReaderIndex();//复位读指针
            return;
        }
        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        Msg msg = null;

        msg = (Msg)Class.forName("com.msb.tank.net." + msgType.toString() + "Msg")
                .getDeclaredConstructor()
                .newInstance();
        //方法二
        /*switch (msgType){
            case TankJoin:
                msg = new TankJoinMsg();
                msg.parse(bytes);
                break;
            case TankStartMoving:
                msg = new TankStartMovingMsg();
                msg.parse();
                break;
        }*/
        msg.parse(bytes);
        list.add(msg);
    }
}
