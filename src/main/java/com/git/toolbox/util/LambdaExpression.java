package com.git.toolbox.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by poan on 2017/08/15.
 */
public class LambdaExpression {


    public static void main(String[] args) {

        SupplierTest<String> stringFactory = new SupplierTest<>(() -> Arrays.asList("1", "2"));

    }


}

class SupplierTest<T> {

    private Supplier<List<T>> supplier;

    public SupplierTest() {
        throw new UnsupportedOperationException();
    }

    public SupplierTest(Supplier<List<T>> supplier) {
        this.supplier = supplier;

        System.out.println(supplier.get());
    }
}


