package com.msb.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 15:37
 * @description: com.msb.tank
 * @version: 1.0
 */
public class PropertyMgr {
    private static Properties props;
    static {
        try {
            props = new Properties();
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String Key){
        return (String)props.get(Key);
    }
}
