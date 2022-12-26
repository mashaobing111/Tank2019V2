package com.msb.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author: msb
 * @date: 2022/12/24 - 12 - 24 - 16:09
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class Server {
    private ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public void serverStart() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(2);//负责接待
        EventLoopGroup workGroup = new NioEventLoopGroup(4);//负责处理

        ServerBootstrap b = new ServerBootstrap();//辅助启动类
        try {
           ChannelFuture future = b.group(bossGroup,workGroup)//添加线程
                    .channel(NioServerSocketChannel.class)//添加通道类型
                    .childHandler(new ChannelInitializer<SocketChannel>() {//通道初始化
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChildServerHandler());
                        }
                    })
                    .bind(8888)//绑定本地端口
                    .sync();//连接阻塞
            ServerFrame.INSTANCE.updateServer("Server Started!");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
     class ChildServerHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            clients.writeAndFlush(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            clients.remove(ctx);
            ctx.close();
        }
    }
}
