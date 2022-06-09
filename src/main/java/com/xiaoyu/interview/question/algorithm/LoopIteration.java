package com.xiaoyu.interview.question.algorithm;

/**
 * Description 有n步台阶，一次只能上1步或2步，共有多少种走法问题
 *
 * @author 小雨
 * createTime 2022年03月06日 12:15:00
 */
public class LoopIteration {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(loopIteration(42));
        long end = System.currentTimeMillis();
        System.out.printf("耗时毫秒为：%d", end - start);
    }

    /**
     * 循环迭代解决 有n步台阶，一次只能上1步或2步，共有多少种走法问题
     *
     * @param n 待计算的值（台阶数）
     * @return 结果（多少种走法）
     */
    public static int loopIteration(int n) {
        if (n < 1) {
            throw new IllegalArgumentException(n + "不能小于1");
        }
        if (n == 1 || n == 2) {
            return n;
        }
        // 当n=3时，one=f(3-1)=f(2)=2,two=f(3-2)=f(1)=1
        int one = 2; // 初始化走到最后需要走一步的走法
        int two = 1; // 初始化走到最后需要走两步的走法
        // 总步数
        int sum = 0;
        for (int i = 3; i <= n; i++) {
            sum = one + two;
            // 当i=4时，此时two=f(4-2)=f(2)=2，所以就是上一次的one
            two = one;
            // 当i=4时，one=f(4-1)=f(3)=3，所以就是上一次的sum
            one = sum;
        }
        return sum;
    }

    /**
     * 有n步台阶，一次只能上1步或2步，共有多少种走法的递归代码实现
     * 性能损耗比较严重，特别是数据量大的时候，还会有可能造成栈溢出
     *
     * @param n 待计算的值（台阶数）
     * @return 结果（多少种走法）
     */
    public static int recursion(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        return recursion(n - 2) + recursion(n - 1);
    }

}
