
package com.keven1z.netty;

import com.keven1z.entity.ClassInfoEntity;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class HeartbeatInitializer extends ChannelInitializer<Channel> {
    public static BlockingQueue<CustomProtocol> HeartQueue = new LinkedBlockingQueue<>(10);
    public static Map<String, String> CodeMap = new ConcurrentHashMap<>();
    public static Set<ClassInfoEntity> ClassMap = new CopyOnWriteArraySet<>();

    public final static int ACTION_GET_CODE = 1;
    public final static int ACTION_GET_CLASS = 2;


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                //五秒没有收到消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(5, 0, 0))
                .addLast(new HeartbeatEncode())
                .addLast(new HeartbeatDecoder())
                .addLast(new HeartBeatSimpleHandle());
    }
}

