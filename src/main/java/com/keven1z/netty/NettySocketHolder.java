
package com.keven1z.netty;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class NettySocketHolder {
    private static final Map<String, NioSocketChannel> MAP = new ConcurrentHashMap<>(16);

    public static void put(String id, NioSocketChannel socketChannel) {
        MAP.put(id, socketChannel);
    }

    public static NioSocketChannel get(String id) {
        return MAP.get(id);
    }
    public static String get(NioSocketChannel nioSocketChannel) {
        String id = null;
        Optional<Map.Entry<String, NioSocketChannel>> first = MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).findFirst();
        if(first.isPresent()){
            id = first.get().getKey();
        }
        return id;
    }

    public static Map<String, NioSocketChannel> getMAP() {
        return MAP;
    }

    public static void remove(NioSocketChannel nioSocketChannel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> MAP.remove(entry.getKey()));
    }
}

