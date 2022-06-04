package com.xiaoyu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description 验证volatile不保证原子性
 * 原子性：不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被赛或者被分割。需要整体完整，要么同时成功，要么同时失败。
 * 保证数据的完整一致性
 *
 * @author 小雨
 * createTime 2022年05月14日 23:39:00
 */
public class VolatileDemo {

    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }
        // 需要等待上面20个线程都全部计算完成后，再用main线程取得最终的结果值看是多少？
        // 大于2 的原因在于默认就有两个线程，main和GC
        while (Thread.activeCount() > 2) {
            // 如果还有其他线程在执行，就礼让
            Thread.yield();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t finally number atomicInteger:" + myData.atomicInteger);
    }

}

class MyData {

    volatile int number = 0;

    /**
     * 请注意，此时number是加了volatile关键字修饰的
     */
    public void addPlusPlus() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic() {
         atomicInteger.getAndIncrement();
    }

}

