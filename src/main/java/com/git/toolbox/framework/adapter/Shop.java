package com.git.toolbox.framework.adapter;


/**
 * 适配器模式：原本是想看看“挂羊头卖狗肉”是否可以
 * 能使用适配器实现，场景如：只有羊(Sheep)，而顾客(Customer)
 * 要求上狗肉，店家(Shop)只好做了只假狗(FakeDog)来蒙混顾客。
 * 对于Dog实体，原本是想定义getDog返回Dog实例，但是，适配实际上
 * 只能“模仿”Dog的行为，只能定义一个void方法。。。
 */
public class Shop {


    public static void main(String[] args) {
        Customer customer = new Customer(new FakeDog());
        customer.guard();

    }
}
