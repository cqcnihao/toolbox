package com.git.toolbox.framework.flyweight;

/**
 * Created by poan on 2017/12/01.
 */
public class Soil implements Element {

    @Override
    public void refine() {
        System.out.println("Soil精灵" + System.identityHashCode(this));
    }
}