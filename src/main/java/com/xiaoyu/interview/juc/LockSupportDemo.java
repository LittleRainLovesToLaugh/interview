package com.xiaoyu.interview.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description 使用Lock上锁，condition阻塞线程和唤醒线程
 * await方法以及signal方法需要配合Lock锁一起使用，否则会出现：IllegalMonitorStateException异常
 * 同理：如果先执行signal方法，再执行await方法，如下：睡眠线程A几秒钟，让线程B先执行，就会出现A一直等待的情况
 */
public class LockSupportDemo {
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            // 暂停线程几秒钟
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---被唤醒");
            } finally {
                lock.unlock();
            }
        }, "A").start();
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "唤醒A线程动作");
            } finally {
                lock.unlock();
            }
        }, "B").start();
    }
}
