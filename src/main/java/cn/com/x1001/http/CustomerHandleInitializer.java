package cn.com.x1001.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class CustomerHandleInitializer extends ChannelInitializer<Channel> {
    private String agentId;
    public  CustomerHandleInitializer(String agentId){
        this.agentId = agentId;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                // 每两秒发送一次心跳
                .addLast(new IdleStateHandler(0, 2, 0))
                .addLast(new HeartbeatEncode())
                .addLast(new HeartBeatClient(agentId))
        ;
    }
}
