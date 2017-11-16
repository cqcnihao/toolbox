package com.git.toolbox.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by poan on 2017/11/03.
 */

class InviteCode {

    /**
     * 1 已经使用 0 未使用
     */
    private int status;

    public InviteCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


public class ConcurrentTest {

    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        InviteCode inviteCode = new InviteCode(0);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                        System.out.println(System.currentTimeMillis());
                        use(inviteCode);
                    }
            );
        }
        if (executorService.isTerminated() || executorService.isShutdown()) {
            System.exit(0);
        }

    }

    public static void use(InviteCode inviteCode) {
        /**
         * 如果InvitCode是与数据库记录对应的DTO对象，
         * 那么这里用inviteCode的状态读取是有问题的！！！！
         * 实测多个用户(A,B,C,D)争抢一个邀请码的时候(如果是多实例情况：假设ABCD都访问了同一个服务器实例)，
         * A先占有了锁，BCD等待，当A操作完毕；BCD如果再从数据库中获取InviteCode的状态，这时可能会由于事务
         * 问题——A所在Http请求未结束，导致InviteCode的状态未更新，从而BCD再读到的状态是错误的；
         * （Flask中，一个Http请求涉及到的数据库中字段的操作，只有请求结束时才会执行）
         *
         * 所以为了是ABCD读取到的邀请码状态是准确的 ， 将邀请码的状态计入一个公共的服务器上的Redis中；
         *
         * 读取内存中InviteCode的状态也是不对的，因为多实例的状态下，各个实力的内存又不共享；所以还是计入
         * Redis中
         */
        if (inviteCode.getStatus() == 0) {
            try {
                lock.writeLock().lock();
                if (inviteCode.getStatus() == 0) {
                    inviteCode.setStatus(1);
                    System.out.println("已激活！");
                } else {
                    System.out.println("卡已经被使用！");
                    throw new RuntimeException("卡已经被激活");
                }

            } finally {
                lock.writeLock().unlock();
            }

        }
    }


}
