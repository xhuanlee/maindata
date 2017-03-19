package com.kd.maindata.utils;

import java.util.UUID;

/**
 * Created by xhuanlee on 2017/3/19.
 */
public class StringUtil {

    /**
     * 获取唯一标识码UUID
     * @return UUID字符串
     */
    public static String UUID() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString().replaceAll("-", "");
    }

}
