package com.msb.tank.net;

/**
 * @author: msb
 * @date: 2022/12/27 - 12 - 27 - 14:26
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public abstract class Msg {
    public abstract byte[] toBytes();

    public abstract void parse(byte[] bytes);

    public abstract void handle();

    public abstract MsgType getMsgType();
}
