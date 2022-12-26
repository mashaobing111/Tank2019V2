package com.msb.callable;

import java.util.concurrent.Callable;

/**
 * @author: msb
 * @date: 2022/12/21 - 12 - 21 - 17:00
 * @description: com.msb.callable
 * @version: 1.0
 */
public class MyTask implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        long r = 0L;
        for (long i = 0L; i <10L ; i++) {
            r += i;
            Thread.sleep(500);
            System.out.println(i + "added !");
        }
        return r;
    }
}