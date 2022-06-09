package com.xiaoyu.interview.juc;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description 题目：多线程按顺序调用，实现A->B->C三个线程启动，要求如下：
 * A打印5次，B打印10次，C打印15次，循环打印10轮
 *
 * @author 小雨
 * createTime 2022年06月06日 17:27:00
 */
public class ConditionRouseDemo {

    public static void main(String[] args) {
        SharResource sharResource = new SharResource();
        for (int i = 0; i < 10; i++) {
            new Thread(sharResource::printA, "A").start();
            new Thread(sharResource::printB, "B").start();
            new Thread(sharResource::printC, "C").start();
        }
    }

}

class SharResource {

    private int number = 1; // A:1 B:2 C:3

    private final Lock lock = new ReentrantLock();

    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private final Condition conditionC = lock.newCondition();


    public void printA() {
        lock.lock();
        try {
            while (number != 1) {
                conditionA.await();
            }
            print(5);
            number = 2;
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (number != 2) {
                conditionB.await();
            }
            print(10);
            number = 3;
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (number != 3) {
                conditionC.await();
            }
            print(15);
            number = 1;
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印方法
     *
     * @param printNumber 打印次数
     */
    public void print(int printNumber) {
        for (int i = 1; i <= printNumber; i++) {
            System.out.println(Thread.currentThread().getName() + "\t" + i);
        }
    }


}
