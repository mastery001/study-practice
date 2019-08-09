import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zouziwen on 2019/3/17.
 */
public class EchoServer {

    private static final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup(1);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(group)
//                    .option(ChannelOption.SO_BACKLOG , 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
//                    .handler(new ChannelOutboundHandlerAdapter(){
//                        @Override
//                        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
//                            System.out.println("bind: " + localAddress.toString());
//                            ctx.bind(localAddress , promise);
//                        }
//
//                        @Override
//                        public void read(ChannelHandlerContext ctx) throws Exception {
//                            System.out.println("read ");
//                            ctx.read();
//                        }
//
//                        @Override
//                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//                            System.out.println("write ");
//                            ctx.write(msg , promise);
//                        }
//
//                        @Override
//                        public void flush(ChannelHandlerContext ctx) throws Exception {
//                            System.out.println("flush ");
//                            ctx.flush();
//                        }
//                    })
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                    ByteBuf buf = (ByteBuf) msg;
//                                    System.out.println("received : " + buf.toString(Charset.defaultCharset()));
//                                    ctx.write(buf);
                                    System.out.println("read-" + counter.getAndIncrement() + ":" + ctx.channel().id() + " :" + msg);
                                    ctx.writeAndFlush(1).addListener(ChannelFutureListener.CLOSE);
//                                    super.channelRead(ctx , msg);
    //                                super.channelRead(ctx, msg);
                                }

//                                @Override
//                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//                                    ctx.writeAndFlush(this.msg).addListener(ChannelFutureListener.CLOSE);
//    //                                super.channelReadComplete(ctx);
//                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                    ctx.close();
    //                                super.exceptionCaught(ctx, cause);
                                }
                            });
                        }
                    });

            ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080)).sync();

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }
}
