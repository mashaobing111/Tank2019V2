package com.msb.tank.chainofresponsibility;

import com.msb.tank.AbstractGameObject;

import java.io.Serializable;

/**
 * @author: msb
 * @date: 2022/12/16 - 12 - 16 - 20:07
 * @description: com.msb.tank.chainofresponsibility
 * @version: 1.0
 */
public interface Collider extends Serializable {
    //return true：chain go on ， return false ：chain break；
    public boolean collide(AbstractGameObject ago1, AbstractGameObject ago2);
}
