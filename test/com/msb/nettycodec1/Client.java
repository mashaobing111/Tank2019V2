package com.msb.nettycodec1;

import com.msb.nettychat.ClientFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @author: msb
 * @date: 2022/12/22 - 12 - 22 - 13:47
 * @description: com.msb.nettychat
 * @version: 1.0
 */
public class Client {
    private Channel channel = null;
    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap();;


        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline()
                            .addLast(new TankMsgEncoder())
                            .addLast(new TankMsgDecoder())
                            .addLast(new ClientHandler());
                }
            });
            ChannelFuture future = b.connect("localhost", 8888).sync();
            System.out.println("connected to Server");
            //等待关闭
            future.channel().closeFuture().sync();
            System.out.println("go on!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }

    }

    public void send(String text) {
        channel.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));
    }

    public void closeConnection() {
        this.send("__bye__");
        channel.close();
    }

    static class ClientHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("aaa");
            ctx.writeAndFlush(new TankMsg(5,8));
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg){
            System.out.println(msg.toString());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }
}
