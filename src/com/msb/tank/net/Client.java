package com.msb.tank.net;

import com.msb.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: msb
 * @date: 2022/12/24 - 12 - 24 - 16:45
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class Client {
    public static final Client INSTANCE = new Client();
    private Channel channel = null;

    private Client (){};
    public void connect(){
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap();
        try {
           ChannelFuture future = b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            channel = socketChannel;
                            socketChannel.pipeline()
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost",8888).sync();
            System.out.println("connected to server");
            //等待关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    public void send(Msg msg){
        channel.writeAndFlush(msg);
    }
    public void closeConnection(){
        channel.close();
    }
    class ClientHandler extends SimpleChannelInboundHandler<Msg>{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
        }



        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
            System.out.println(msg.toString());
            msg.handle();
        }
    }
}
