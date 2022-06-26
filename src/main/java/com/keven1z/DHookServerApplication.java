package com.keven1z;

import io.netty.channel.ChannelFuture;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.annotation.Resource;
import java.io.IOException;

@SpringBootApplication
@MapperScan(basePackages = {"com.keven1z.dao"})
public class DHookServerApplication  implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    @Resource
    NettyServer nettyServer;
    public static void main(String[] args) {
        SpringApplication.run(DHookServerApplication.class, args);
    }
    @Override
    public void run(String... args) throws InterruptedException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 开启服务
        int port = 7070;
        ChannelFuture future = nettyServer.start("localhost", port);
        if (future.isSuccess()){
            logger.info("Netty started on port(s): "+port);
        }
        // 在JVM销毁前关闭服务
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                nettyServer.close();
            }
        });
        future.channel().closeFuture().sync();

    }
}
