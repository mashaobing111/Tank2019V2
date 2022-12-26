package com.msb.net.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: msb
 * @date: 2022/12/21 - 12 - 21 - 14:45
 * @description: com.msb.net.io.netty
 * @version: 1.0
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        //负责接客
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workGroup = new NioEventLoopGroup(4);
        //Server辅助启动类
        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup,workGroup);
        //异步全双工
        b.channel(NioServerSocketChannel.class);//指定用什么通道进行连接
        //netty 帮我们内部处理了accept的过程
        b.childHandler(new MyChildInitializer());//服务器用childHandler
        ChannelFuture future = b.bind(8888).sync();

        future.channel().closeFuture().channel();

        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

}
class MyChildInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ChildHandler());
    }
}
class ChildHandler extends ChannelInboundHandlerAdapter{//连上来的通道处理器
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString());
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}