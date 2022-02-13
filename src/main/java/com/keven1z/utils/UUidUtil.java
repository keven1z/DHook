package com.keven1z.utils;

import java.util.UUID;

/**
 * @author keven1z
 * @date 2021/12/22
 */
public class UUidUtil {
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
