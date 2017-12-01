package com.git.toolbox.framework.flyweight;

/**
 * Created by poan on 2017/12/01.
 */
public class Wood implements Element {

    @Override
    public void refine() {
        System.out.println("Wood精灵" + System.identityHashCode(this));
    }
}