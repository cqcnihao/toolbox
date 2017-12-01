package com.git.toolbox.framework.flyweight;

/**
 * 享元模式：
 * 就好比宠物小精灵的世界中，有很多很多的精灵，虽然都长得一样，
 * 但其实都是各自不通的个体；但是他们有共同的属性，比如水系精灵，
 * 火系精灵等等，这些共同的属性可以抽象出来，先创建~ 不用每个精灵的属性
 * 都要再创建一次~
 * <p>
 * <p>
 * 或者你也可以看看Integer的valueOf方法~
 */
public class App {

    public static void main(String[] args) {
        ElementFactory elementFactory = new ElementFactory();
        elementFactory.createElement(ElementEnum.Fire).refine();
        elementFactory.createElement(ElementEnum.Fire).refine();
        elementFactory.createElement(ElementEnum.Fire).refine();

        elementFactory.createElement(ElementEnum.Water).refine();
        elementFactory.createElement(ElementEnum.Water).refine();


        elementFactory.createElement(ElementEnum.Wood).refine();
        elementFactory.createElement(ElementEnum.Wood).refine();
        elementFactory.createElement(ElementEnum.Wood).refine();

        elementFactory.createElement(ElementEnum.Soil).refine();
        elementFactory.createElement(ElementEnum.Soil).refine();

        elementFactory.createElement(ElementEnum.Gold).refine();
        elementFactory.createElement(ElementEnum.Gold).refine();
        elementFactory.createElement(ElementEnum.Gold).refine();
    }


}
