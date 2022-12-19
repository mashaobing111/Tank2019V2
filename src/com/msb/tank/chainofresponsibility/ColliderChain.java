package com.msb.tank.chainofresponsibility;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.PropertyMgr;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: msb
 * @date: 2022/12/17 - 12 - 17 - 16:01
 * @description: com.msb.tank.chainofresponsibility
 * @version: 1.0
 */
public class ColliderChain implements Collider {
    List<Collider> colliders;

    public ColliderChain() {
        initCollider();
    }
    private void initCollider() {
        colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        try {
            for (String name : colliderNames){
                Class clazz = Class.forName("com.msb.tank.chainofresponsibility." + name);
                Collider c = (Collider) clazz.getConstructor().newInstance();
                colliders.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2){
        for (Collider collider : colliders) {
           if (!collider.collide(go1, go2)){
               return false;
           }
        }
        return true;
    }

}
