package com.ydt.util;

import java.util.UUID;

/**
 * UUID生成器
 */
public class IdGenerator {
    public static int seq = 0;
    public static byte[] lock = new byte[0];

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }
}
