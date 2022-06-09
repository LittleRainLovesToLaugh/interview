package com.xiaoyu.interview.juc;

/**
 * Description 死锁代码演示
 *
 * @author 小雨
 * createTime 2022年06月07日 20:51:00
 */
public class DeadlyEmbraceDemo {
}

class MyThread implements Runnable {

    private final String lockA;

    private final String lockB;

    public MyThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

    }
}