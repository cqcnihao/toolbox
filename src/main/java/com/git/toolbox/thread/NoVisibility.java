package com.git.toolbox.thread;

import java.util.concurrent.Executors;

/**
 * Created by poan on 2018/01/05.
 */
public class NoVisibility {


    // 在类加载的机制的准备阶段，static的变量会被赋予初始值
    private static boolean ready;// 默认 false

    private static int number; // 默认 0


    /**
     * 以下的代码输出的是0；并且循环不会终止；
     * 分析：
     * execute的方法不会立即实行，也就是说：线程之后的代码执行
     * 顺序是不能保障的， while (!ready) 的执行可能在 ready = true
     * 之前或者是之后； 如果在ready的读取操作在赋值操作之前， 那么
     * 主线程输出的就是number的默认值，并且线程会一直等待下去；；；
     *
     *
     */
    public static void main(String[] args) {
        Executors.newFixedThreadPool(1).execute(() -> {
            while (!ready) {
                Thread.yield(); // 将CPU的使用转让出去，不过自己也可以抢
            }
        }); // 线程去执行
        System.out.println(number); // 主线程执行

        number = 42;
        ready = true;
    }

}
