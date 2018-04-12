package com.git.poan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by poan on 2018/04/12.
 */
public class WaitNotityTest {


    public static void bake() {

        synchronized (WaitNotityTest.class) {
            for (int i = 1; i <= 10; i++) {

                Class<WaitNotityTest> waitNotityTestClass = WaitNotityTest.class;
                try {
                    Thread.sleep(3000);
                    System.out.println("烤好第" + i + "个面包");
                    waitNotityTestClass.wait();
                    waitNotityTestClass.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void eat() {
        synchronized (WaitNotityTest.class) {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(3000);
                    System.out.println("吃了第" + i + "个面包");
                    Class<WaitNotityTest> waitNotityTestClass = WaitNotityTest.class;
                    waitNotityTestClass.notify();
                    waitNotityTestClass.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ExecutorService baker = Executors.newFixedThreadPool(3);
        ExecutorService eater = Executors.newFixedThreadPool(3);


        baker.execute(WaitNotityTest::bake);
        eater.execute(WaitNotityTest::eat);
    }

}
