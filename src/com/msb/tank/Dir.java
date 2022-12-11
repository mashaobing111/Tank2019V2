package com.msb.tank;

/**
 * @author: msb
 * @date: 2022/12/9 - 12 - 09 - 15:59
 * @description: com.msb.tank
 * @version: 1.0
 */

public enum Dir {
    U, D, L, R;
}
/*
为什么Enum 比 int类型号？
a.编译期间就能知道赋值是不是有问题
int dir = 1， 2， 3， 4,  dir = 5 ；如果是int类型 这样只有在运行的时候才知道会出现问题
* */
