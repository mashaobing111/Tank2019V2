package com.msb.nettycodec1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author: msb
 * @date: 2022/12/22 - 12 - 22 - 14:14
 * @description: com.msb.nettychat
 * @version: 1.0
 */
public class Server {
    //创建一个通道做 用于存储通道
    public  ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public  void serverStart(){
        //负责接受
        EventLoopGroup boosGroup  = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workGroup  = new NioEventLoopGroup(4);

        try {
            //辅助启动类
            ServerBootstrap b = new ServerBootstrap();

            ChannelFuture future= b.group(boosGroup,workGroup)
                //异步全双工
                .channel(NioServerSocketChannel.class)
                //通道初始化 netty 帮我们在内部处理了accept的过程
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ServerChildHandler());
                    }
                })
                .bind(8888)
                .sync();
            ServerFrame.INSTANCE.updateServerMsg("server started!");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    class  ServerChildHandler extends ChannelInboundHandlerAdapter{
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
            clients.remove(ctx.channel());
            ctx.close();
        }
    }
}


