package com.msb.tank.strategy;

import com.msb.tank.Player;

import java.io.Serializable;


/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 16:09
 * @description: com.msb.tank.strategy
 * @version: 1.0
 */
public interface FireStrategy extends Serializable {
    public void fire(Player t);
}
