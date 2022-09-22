
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

        int i = in.readInt();
        byte[] byte_id = new byte[32] ;
        in.readBytes(byte_id);
        String id = new String(byte_id);

        byte[] bytes = new byte[in.readableBytes()] ;
        in.readBytes(bytes);
        String body = new String(bytes);
        CustomProtocol customProtocol = new CustomProtocol() ;
        customProtocol.setId(id);
        customProtocol.setAction(i);
        customProtocol.setBody(body);
        out.add(customProtocol);
    }
}
