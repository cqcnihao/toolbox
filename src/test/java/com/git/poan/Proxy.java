package com.git.poan;

/**
 * Created by poan on 2018/04/03.
 */
public class Proxy {

    public static void main(String[] args) {
        Clerk clerk = new Clerk(new Boss());
        clerk.meeting();
    }

}

class Boss {

    public void meeting() {
        System.out.println("不错不错");
    }

}

class Clerk {

    private Boss boss;

    public Clerk(Boss boss) {
        this.boss = boss;
    }

    public void meeting() {

        System.out.println("会议开始:");
        for (int i = 0; i < 10; i++) {
            System.out.println("!@#$$%^^&*");
        }
        boss.meeting();
        System.out.println("会议结束");
    }

}
