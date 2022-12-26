package com.msb.net.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author: msb
 * @date: 2022/12/21 - 12 - 21 - 16:15
 * @description: com.msb.net.io.netty
 * @version: 1.0
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        //辅助启动类
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new MyHandler());
            }
        });
        ChannelFuture future = b.connect("localhost",8888).sync();
        //System.in.read();
        //等待关闭
        future.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }
   static class MyHandler extends ChannelInboundHandlerAdapter{
       @Override
       public void channelActive(ChannelHandlerContext ctx) throws Exception {
           ByteBuf buf = Unpooled.copiedBuffer("msb".getBytes());
           ctx.writeAndFlush(buf);
       }

       @Override
       public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

           System.out.println(msg.toString());
       }

       @Override
       public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
           super.exceptionCaught(ctx, cause);
       }
   }
}
