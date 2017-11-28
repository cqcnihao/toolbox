package com.git.toolbox.framework.adapter;

/**
 * Created by poan on 2017/11/28.
 */
public class Customer {

    private Dog dog;

    public Customer(Dog dog) {
        this.dog = dog;
    }

    public void guard() {
        dog.bark();
    }


}
