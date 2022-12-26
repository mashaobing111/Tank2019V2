package com.msb.callable;

import java.util.concurrent.FutureTask;

/**
 * @author: msb
 * @date: 2022/12/21 - 12 - 21 - 17:21
 * @description: com.msb.callable
 * @version: 1.0
 */
public class FutureTaskTest {
    public static void main(String[] args) throws Exception{
        FutureTask<Long> ft  = new FutureTask<>(new MyTask());
        new Thread(ft).start();
        Long l = ft.get();
        System.out.println(l);
    }
}
