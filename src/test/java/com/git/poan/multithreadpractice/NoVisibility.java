package com.git.poan.multithreadpractice;

public class NoVisibility {


    private volatile static boolean ready;

    private volatile static int number;


    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10000; i++) {


            Thread readThread = new Thread(() -> {
                while (!ready) {
                    Thread.yield();
                }
                System.out.println(number);
            });
            readThread.start();// 只是处于就绪状态，理论上会很快就执行run
            number = 42; // 这一步是主线程执行，readThread不一定会读取到这个变量，读不到的话就是默认值0
            ready = true;// 这一步是主线程执行，readThread也不一定能读取到这个变量；读不到的话就是默认值false

        }
    }

}
