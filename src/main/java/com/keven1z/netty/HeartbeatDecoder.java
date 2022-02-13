
package com.keven1z.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class HeartbeatDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] bytes = new byte[in.readableBytes()] ;
        in.readBytes(bytes);
        String id = new String(bytes);
        CustomProtocol customProtocol = new CustomProtocol() ;
        customProtocol.setId(id);
        out.add(customProtocol) ;
    }
}
