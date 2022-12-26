package com.msb.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: msb
 * @date: 2022/12/21 - 12 - 21 - 17:10
 * @description: com.msb.callable
 * @version: 1.0
 */
public class CallableAndFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Long> future = service.submit(new MyTask());

        long l = future.get();//wait until there's a result  --- blocked!//阻塞方法。等待返回值
        System.out.println(l);

        System.out.println("go on!");
    }
}
