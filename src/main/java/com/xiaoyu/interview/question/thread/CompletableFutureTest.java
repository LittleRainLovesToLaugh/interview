package com.xiaoyu.interview.question.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description守护线程中途死亡演示， CompletableFuture：默认就是以守护线程运行，会出现中途死亡的情况，加上指定运行的线程池参数就不会以守护线程运行
 *
 * @author 小雨
 * createTime 2022年04月17日 21:38:00
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 1.执行时间 < 主线程，会打印
        CompletableFuture.runAsync(() ->
                System.out.println("Thread1 是否为守护线程 : " + Thread.currentThread().isDaemon()));

        // 2.执行时间 > 主线程，是守护线程，会被杀死，不会打印
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000L);
                System.out.println("Thread2 是否为守护线程 : " + Thread.currentThread().isDaemon());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 3.执行时间 > 主线程，但是不是守护线程，不会被杀死，会打印
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000L);
                System.out.println("Thread3 等待1秒");
                System.out.println("Thread3 是否为守护线程 : " + Thread.currentThread().isDaemon());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService);

        // 4.ExecutorService.submit()，默认不是守护线程，不会被杀死，会打印。
        executorService.submit(() -> {
            try {
                Thread.sleep(2000L);
                System.out.println("Thread4 等待2秒");
                System.out.println("Thread4 是否为守护线程 : " + Thread.currentThread().isDaemon());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 主线程执行完毕
        System.out.println("Main Thread Finished.");
        executorService.shutdown();
    }
}
