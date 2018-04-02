package com.git.poan.designparttern;

/**
 * Created by poan on 2018/04/02.
 */
public enum ResponsibilityChain {


    FIRST {
        @Override
        public void dose() {
            System.out.println("我要说，123456789； 然后接下来有请下一位");
        }
    },
    SECOND {
        @Override
        public void dose() {
            System.out.println("我要说 abcdefgh； 然后接下来有请下一位");
        }
    },
    THIRD {
        @Override
        public void dose() {
            System.out.println("我要说 @！#￥%……&*； 然后接下来有请下一位");
        }
    },;

    public void speak() {
        System.out.println("有请：" + this.name());
        dose();
    }

    protected abstract void dose();


    public static void main(String[] args) {
        ResponsibilityChain[] chains = ResponsibilityChain.values();
        for (ResponsibilityChain chain : chains) {
            chain.speak();
        }
    }
}
