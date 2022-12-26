package com.msb.nettycodec1;

/**
 * @author: msb
 * @date: 2022/12/24 - 12 - 24 - 14:49
 * @description: com.msb.nettycodec1
 * @version: 1.0
 */
public class TankMsg {
    public int x, y;

    public TankMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
