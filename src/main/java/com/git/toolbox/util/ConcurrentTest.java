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
