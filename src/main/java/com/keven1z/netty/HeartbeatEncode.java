package com.keven1z.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class HeartbeatEncode extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getAction());
        out.writeBytes(msg.getId().getBytes(StandardCharsets.UTF_8));
        out.writeBytes(msg.getBody().getBytes(StandardCharsets.UTF_8));

    }
}

