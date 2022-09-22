
package com.keven1z;

import com.keven1z.netty.HeartbeatInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * @author keven1z
 * @date 2021/12/23
 */
@Component
public class NettyServer {
    private static final Logger  logger = LoggerFactory.getLogger(NettyServer.class);
    // 服务端NIO线程组
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    public ChannelFuture  start(String host, int port){
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 自定义服务处理
                            socketChannel.pipeline().addLast(new HeartbeatInitializer());
                        }
                    });
            // 绑定端口并同步等待
            channelFuture = bootstrap.bind(host, port).sync();

        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        return channelFuture;
    }
    public void close() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        logger.info("Shutdown Netty Server");
    }

}

