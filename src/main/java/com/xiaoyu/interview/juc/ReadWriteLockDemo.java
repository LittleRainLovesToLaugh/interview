package com.xiaoyu.interview.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Description 读写锁：多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是如果有一个线程想去写共享资源，就不应该再有其他线程可以对该资源进行读或写
 * 总结：读——读能共存 ，读——写不能共存，写——写不能共存
 *
 * @author 小雨
 * createTime 2022年06月05日 16:31:00
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> myCache.put(temp + "", temp + ""), String.valueOf(i)).start();
        }
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> myCache.get(temp + ""), String.valueOf(i)).start();
        }

    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        // 获取写锁
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在写入" + key);
            map.put(key, value);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void get(String key) {
        // 获取读锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读取");
            Object result = map.get(key);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t读取完成" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }

}