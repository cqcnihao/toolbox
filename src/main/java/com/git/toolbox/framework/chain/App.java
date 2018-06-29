package com.git.toolbox.framework.chain;

/**
 * @Author: panbenxing
 * @Date: 2018/6/29
 * @Description:
 */
public class App {


    public static void main(String[] args) {

        Layer layer = new FirstLayer().next(new SecondLayer().next(new ThirdLayer()));

        layer.doSomething();

    }
}
