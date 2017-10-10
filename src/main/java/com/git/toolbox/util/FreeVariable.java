package com.git.toolbox.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by poan on 2017/10/10.
 */
public class FreeVariable {

    /*function count() {
        var arr = [];
        for (var i=1; i<=3; i++) {
            arr.push(function () {
                return i * i;
            });
        }
        return arr;

    }*/

    public static List<Supplier<Integer>> count() {

        List<Supplier<Integer>> suppliers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {

            final int t = i;
            suppliers.add(() -> t * t);


        }


        return suppliers;

    }

    public static void main(String[] args) {
        List<Supplier<Integer>> count = count();
        System.out.println(count.get(0).get());
        System.out.println(count.get(1).get());
        System.out.println(count.get(2).get());
    }


}
