package com.git.poan.multithreadpractice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnsafeSequence {

    private int val = 1;

    public int getNextVal() {
        return ++val;
    }

    public static void main(String[] args) {


        int threadNum = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        for (int i = 0; i < threadNum; i++) {

            final int j = i;

            executorService.execute(() -> {

                Thread.currentThread().setName("thread-" + j);
                int nextVal = unsafeSequence.getNextVal();
                System.out.println(Thread.currentThread().getName() + "'s val:" + nextVal);
            });
        }


    }

}
