
package com.keven1z.netty;

import com.keven1z.utils.AgentUtil;
import com.keven1z.entity.AgentEntity;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class HeartBeatSimpleHandle extends SimpleChannelInboundHandler<CustomProtocol> {

    private final static Logger LOGGER = LoggerFactory.getLogger(HeartBeatSimpleHandle.class);


    /**
     * 取消绑定
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String id = NettySocketHolder.get((NioSocketChannel) channel);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 5000);
        String time = sdf.format(timestamp);
        AgentUtil.update(id, 0, time);
        LOGGER.info(id + " 断开连接");
        NettySocketHolder.remove((NioSocketChannel) ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                //向客户端发送消息
            }
        }

        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol customProtocol) throws Exception {
//        LOGGER.info("收到customProtocol={}", customProtocol);
        //保存客户端与 Channel 之间的关系
        if (NettySocketHolder.getMAP().containsKey(customProtocol.getId())) return;
        NettySocketHolder.put(customProtocol.getId(), (NioSocketChannel) ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage());
    }
}


