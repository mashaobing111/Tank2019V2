package com.msb.tank;

import java.awt.*;
import java.io.Serializable;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 17:57
 * @description: com.msb.tank
 * @version: 1.0
 */
public abstract class AbstractGameObject implements Serializable {

    public abstract void paint(Graphics g);

    public abstract boolean isLive();
}
