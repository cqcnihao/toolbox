package com.git.toolbox.framework.adapter;

/**
 * Created by poan on 2017/11/28.
 */
public class FakeDog implements Dog {

    private Sheep sheep;

    public FakeDog() {
        this.sheep = new Sheep();
    }

    @Override
    public void bark() {
        sheep.utter();
    }
}
