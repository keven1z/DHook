package cn.com.x1001.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class NettyClient {
    private static final EventLoopGroup group = new NioEventLoopGroup();
    public static void start(String agentId) throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new CustomerHandleInitializer(agentId));
            // 绑定端口并同步等待
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7070).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
