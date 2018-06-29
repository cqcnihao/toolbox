package com.git.toolbox.framework.chain;

/**
 * @Author: panbenxing
 * @Date: 2018/6/29
 * @Description:
 */
public abstract class Layer {


    private Layer nextLayer;


    public void doSomething() {
        System.out.println(String.format("%s层开始处理", this.getClass().getSimpleName()));
        doST();
        if (this.hasNext()) {
            nextLayer.doSomething();
        }

    }

    public abstract void doST();


    public Layer getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }


    public Layer next(Layer layer) {
        setNextLayer(layer);
        return this;
    }


    public boolean hasNext() {
        return this.nextLayer != null;
    }

}
